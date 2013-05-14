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

##########
# configuration
# job name from jenkins, they are expected to be URL encoded already



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

def deploy(processname):
    for line in os.popen("ps xa | grep %s" % processname):
        pid = line.split()[0]
        if "java" in line and processname in line :
            print "Kill the process %s" % pid
            # Kill the Process.
            os.kill(int(pid), signal.SIGTERM)
            #leave 3 seconds to terminate properly
            time.sleep(3)
            os.kill(int(pid), signal.SIGKILL)
            break

    # Starting application
    cmd = "sh start -Dconfig.resource=" + play_conf_resource
    subprocess.Popen(cmd, shell=True)


def main(): 
    print "\t -- Fetching artifacts from Jenkins --"

    if not os.path.exists(workDir):
        raise Exception("\t ~ Error: Unable to find work directory")

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
    deploy(artName.strip(artifactExtension))
    print "\t -- Done --"
    buf.close()
    sys.exit(0)

############################
# Start
main()