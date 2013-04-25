// @SOURCE:/vagrant/conf/routes
// @HASH:61000cb6e75afc95504e3580087378bf4fb2cebc
// @DATE:Thu Apr 25 10:59:25 CEST 2013

import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._


import Router.queryString

object Routes extends Router.Routes {


// @LINE:6
val controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart("/"))))
                    

// @LINE:8
val controllers_Application_tasks1 = Route("GET", PathPattern(List(StaticPart("/tasks"))))
                    

// @LINE:9
val controllers_Application_newTask2 = Route("POST", PathPattern(List(StaticPart("/tasks"))))
                    

// @LINE:10
val controllers_Application_deleteTask3 = Route("POST", PathPattern(List(StaticPart("/tasks/"),DynamicPart("id", """[^/]+"""),StaticPart("/delete"))))
                    

// @LINE:13
val controllers_Assets_at4 = Route("GET", PathPattern(List(StaticPart("/assets/"),DynamicPart("file", """.+"""))))
                    
def documentation = List(("""GET""","""/""","""controllers.Application.index"""),("""GET""","""/tasks""","""controllers.Application.tasks"""),("""POST""","""/tasks""","""controllers.Application.newTask"""),("""POST""","""/tasks/$id<[^/]+>/delete""","""controllers.Application.deleteTask(id:Long)"""),("""GET""","""/assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)"""))
             
    
def routes:PartialFunction[RequestHeader,Handler] = {        

// @LINE:6
case controllers_Application_index0(params) => {
   call { 
        invokeHandler(_root_.controllers.Application.index, HandlerDef(this, "controllers.Application", "index", Nil))
   }
}
                    

// @LINE:8
case controllers_Application_tasks1(params) => {
   call { 
        invokeHandler(_root_.controllers.Application.tasks, HandlerDef(this, "controllers.Application", "tasks", Nil))
   }
}
                    

// @LINE:9
case controllers_Application_newTask2(params) => {
   call { 
        invokeHandler(_root_.controllers.Application.newTask, HandlerDef(this, "controllers.Application", "newTask", Nil))
   }
}
                    

// @LINE:10
case controllers_Application_deleteTask3(params) => {
   call(params.fromPath[Long]("id", None)) { (id) =>
        invokeHandler(_root_.controllers.Application.deleteTask(id), HandlerDef(this, "controllers.Application", "deleteTask", Seq(classOf[Long])))
   }
}
                    

// @LINE:13
case controllers_Assets_at4(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(_root_.controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String])))
   }
}
                    
}
    
}
                