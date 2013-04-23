package lib.filters

import play.api.mvc._
import controllers.routes

object LoginFilter extends Filter {
  def apply(next: (RequestHeader) => Result)(rh: RequestHeader): Result = {

    if (rh.session.get("user") == None
      && (rh.uri.indexOf("login") < 0 && rh.uri.indexOf("assets") < 0)) {
      Results.Redirect(routes.LoginController.login()).flashing("message" -> "当前未登录！")
    } else {
      next(rh)
    }
  }
}
