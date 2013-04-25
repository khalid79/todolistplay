
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import play.api.i18n._
import play.api.mvc._
import play.api.data._
import views.html._
/**/
object index extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template2[List[Task],Form[String],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(tasks: List[Task], taskForm: Form[String]):play.api.templates.Html = {
        _display_ {import helper._


Seq(format.raw/*1.45*/("""

"""),format.raw/*4.1*/("""
"""),_display_(Seq(/*5.2*/main("Todo list")/*5.19*/ {_display_(Seq(format.raw/*5.21*/("""
    
    <h1>"""),_display_(Seq(/*7.10*/tasks/*7.15*/.size)),format.raw/*7.20*/(""" task(s)</h1>
    
    <ul>
        """),_display_(Seq(/*10.10*/tasks/*10.15*/.map/*10.19*/ { task =>_display_(Seq(format.raw/*10.29*/("""
            <li>
                """),_display_(Seq(/*12.18*/task/*12.22*/.label)),format.raw/*12.28*/("""
                
                """),_display_(Seq(/*14.18*/form(routes.Application.deleteTask(task.id))/*14.62*/ {_display_(Seq(format.raw/*14.64*/("""
                    <input type="submit" value="Delete">
                """)))})),format.raw/*16.18*/("""
            </li>
        """)))})),format.raw/*18.10*/("""
    </ul>
    
    <h2>Add a new task</h2>
    
    """),_display_(Seq(/*23.6*/form(routes.Application.newTask)/*23.38*/ {_display_(Seq(format.raw/*23.40*/("""
        
        """),_display_(Seq(/*25.10*/inputText(taskForm("label")))),format.raw/*25.38*/(""" 
        
        <input type="submit" value="Create">
        
    """)))})),format.raw/*29.6*/("""
    
""")))})))}
    }
    
    def render(tasks:List[Task],taskForm:Form[String]) = apply(tasks,taskForm)
    
    def f:((List[Task],Form[String]) => play.api.templates.Html) = (tasks,taskForm) => apply(tasks,taskForm)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Thu Apr 25 10:59:24 CEST 2013
                    SOURCE: /vagrant/app/views/index.scala.html
                    HASH: 7ce0c4da3ed1724e5f9087db7e617651cc76f665
                    MATRIX: 522->1|653->44|681->63|712->65|737->82|771->84|816->99|829->104|855->109|923->146|937->151|950->155|993->165|1059->200|1072->204|1100->210|1166->245|1219->289|1254->291|1361->366|1421->394|1505->448|1546->480|1581->482|1631->501|1681->529|1782->599
                    LINES: 19->1|23->1|25->4|26->5|26->5|26->5|28->7|28->7|28->7|31->10|31->10|31->10|31->10|33->12|33->12|33->12|35->14|35->14|35->14|37->16|39->18|44->23|44->23|44->23|46->25|46->25|50->29
                    -- GENERATED --
                */
            