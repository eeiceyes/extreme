package controllers

import play.api.mvc._
import views.html._
import helpers.GridHelper
import models.Device

object DeviceController extends Controller {
  def index = Action {implicit request =>
    val dev1 = new models.Device("56.5.6.1", "设备1")
    val dev2 = new models.Device("102.102.102.102", "设备2")
    val dev3 = new models.Device("103.103.103.103", "设备3")
    dev1.deviceType = 1
    dev2.deviceType = 1
    dev3.deviceType = 1
    GridHelper.configure(request, routes.DeviceController.index().url, List(dev1, dev2, dev3), 250)
    if(request.headers.get("X-Requested-With").isEmpty)
      Ok(device.index())
    else
      Ok(device.index_table())
  }

  def add = Action {implicit request=>
    Ok(device.add())
  }

  def edit(id: String) = Action {
    Ok(device.edit())
  }

  def create = Action { implicit request =>
    Device.form.bindFromRequest.fold (
      formWithErrors =>
        Ok(device.add()),
      value =>
        Redirect(routes.DeviceController.index()).flashing("message" -> "新增设备成功！")
    )
  }

  def update(id: String) = Action { implicit request=>
    Ok(device.index())
  }

  def delete(id: String) = Action { implicit request=>
    Ok(device.index())
  }

  def reload(id: String) = Action { implicit request=>
    Ok(device.index())
  }
}
