package controllers

import play.api.mvc._
import views.html._
import models.AlarmRule
import helpers.GridHelper

/**
 * Date: 13-4-21
 */
object AlarmRuleController extends Controller{
  def index = Action { implicit request =>

    val rule1 = new AlarmRule(1, "规则1", true, "设备1", 1, "邮件", "2013-01-01")
    val rule2 = new AlarmRule(1, "规则2", true, "设备2", 1, "邮件", "2013-01-01")
    GridHelper.configure(request, routes.AlarmRuleController.index().url, List(rule1, rule2), 250)
    if(request.headers.get("X-Requested-With").isEmpty)
      Ok(alarm_rule.index())
    else
      Ok(alarm_rule.index_table())
  }

  def add = Action {
    Ok(alarm_rule.add())
  }

  def create = Action { implicit request =>
    Ok(alarm_rule.index())
  }
}
