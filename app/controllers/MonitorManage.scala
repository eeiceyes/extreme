package controllers

import play.api.mvc.{Action, Controller}

object MonitorManage extends Controller{
  def index = Action {
    Ok(views.html.monitor_manage.index())
  }
}
