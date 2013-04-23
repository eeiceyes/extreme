package controllers

import play.api.mvc._

object LoginController extends Controller{
  def login = Action { implicit request =>
    Ok(views.html.login.login())
  }

  def auth = Action(parse.urlFormEncoded) {implicit request =>
    val username = request.body.get("username").get(0)
    val password = request.body.get("password").get(0)
    if(username == "admin" && password == "admin") {
      Redirect(routes.TopologyController.view()).withSession(
        session + ("user" -> "admin")
      )
    } else {
      Redirect(routes.LoginController.login()).flashing("message" -> "错误的用户名或密码！")
    }
  }
}
