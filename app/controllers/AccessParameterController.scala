package controllers

import play.api.mvc.{Action, Controller}
import views.html.share.biz._

/**
 * Date: 13-5-3
 */
object AccessParameterController extends Controller {
  def get(access:String) = Action { request =>
    Ok(snmp_parameter())
  }
}
