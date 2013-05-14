#!/usr/bin/env python

import cStringIO
import json
import pycurl
import os
import sys
import base64
import zipfile
import stat
import signal
import subprocess
import shutil
import time
import glob

##########
# configuration
# job name from jenkins, they are expected to be URL encoded already
jobName = "github-test"
build_host = "ec2-54-236-226-98.compute-1.amazonaws.com"
lastBuildAPIURL = "http://" + build_host + "/job/%s/lastSuccessfulBuild/api/json"
lastBuildArtifactLURL = "http://" + build_host + "/job/%s/lastSuccessfulBuild/artifact/%s"
workDir = "/application/directory/work"
artifactExtension=".zip"
user = "admin"
token = "d9ca57326d1d9547bc61205329142ee8"
play_conf_resource = "application.conf"


########## Utils ###########

def downloadFile(url,filename):
    fp = open(filename, "wb")
    curl = pycurl.Curl()
    curl.setopt(curl.HTTPHEADER, ["%s: %s" % t for t in encodeUserData(user,token).items()])
    curl.setopt(pycurl.URL, url)
    curl.setopt(pycurl.WRITEDATA, fp)
    curl.perform()
    curl.close()
    fp.close()

def unZipFile(filename):
    zfile = zipfile.ZipFile(filename)
    for name in zfile.namelist():
        (dirname, filename) = os.path.split(name)
        if filename == '':
            # directory
            if not os.path.exists(dirname):
                os.mkdir(dirname)
        else:
            # file
            fd = open(name, 'w')
            fd.write(zfile.read(name))
            fd.close()
    zfile.close()

# simple wrapper function to encode the username & pass
def encodeUserData(user, token):
    return { 'Authorization' : 'Basic %s' % base64.b64encode(user+':'+token) }

def stopRunningInstance():
    files = glob.glob(workDir+'/*/RUNNING_PID')
    if len(files) > 0:
        pid = runningPid(files[0])
        if pidAlive(pid):
            print "\t ** Stop running instance "
            os.kill(pid, signal.SIGKILL)
       

def deploy():
    cmd = "sh start -Dconfig.resource=" + play_conf_resource
    subprocess.Popen(cmd, shell=True)
   

def runningPid(runningPidFile):
    file = open(runningPidFile, "r")
    content = file.read()
    pid = int(content)
    file.close()
    return pid

def pidAlive(pid):
    try:
        os.kill(pid, 0)
    except OSError, err:
        if err.errno == errno.ESRCH:
            return False
    return True

def main(): 
    print "\t -- Fetching artifacts from Jenkins --"

    if not os.path.exists(workDir):
        raise Exception("\t ~ Error: Unable to find work directory")

    # stop instance
    stopRunningInstance()

    # Cleaning working directory
    for f in glob.glob(workDir+'/*'):
        if os.path.isfile(f):
            os.remove(f)
        else:
            shutil.rmtree(f)

    buf = cStringIO.StringIO()
    jobURL = lastBuildAPIURL % (jobName)
    c = pycurl.Curl()
    c.setopt(c.HTTPHEADER, ["%s: %s" % t for t in encodeUserData(user,token).items()])
    c.setopt(c.URL, jobURL)
    c.setopt(c.WRITEFUNCTION, buf.write)
    c.perform()
    jsonstr = buf.getvalue()
    jd = json.loads(jsonstr)
    buf.close()

    artifacts = jd['artifacts']
    print "\t ---- Artifact informations ----"
    print "\t ** BuildNumber : %s" % jd['number']
    print "\t ** jobName : %s" % jobName
    art = artifacts[0]
    artName = art['fileName']
    if artName.find(artifactExtension) > -1:
        artURL = lastBuildArtifactLURL % (jobName,art['relativePath'])
        print "\t ** Download artifact : %s" % artName
        print "\t ** From : %s" % str(artURL)
        print "\t ** Into : %s" % workDir
        downloadFile(str(artURL),workDir + "/" + str(artName))

    os.chdir(workDir)
    print "\t ** Unzip artifact : %s" % artName
    unZipFile(artName)
    print "\t ** Change start file access permissions"
    os.chdir(artName.strip(artifactExtension))
    os.chmod("start", stat.S_IEXEC)
    print "\t ** Starting application"
    deploy()
    print "\t -- Done --"
    buf.close()
    sys.exit(0)

############################
# Start
main()