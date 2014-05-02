package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok("hi")
  }
  
  def test(id:Long,resourceId:Long) = Action { implicit request =>
    Ok(s"getting url parameters ${request.queryString("id")} and ${request.queryString("resourceId")}")
  }

}