package models

import play.api.data._
import play.api.data.Forms._

case class Device(ip:String, deviceName:String) extends Getter{
  var id: Long = _
  var deviceType:Int = _
  var accessType:Int = _
  var owner:String = _
  var createdTime:String = _
  var deviceDesc:String = _
  var manufacturer:String = _
}

object Device {
  def form = Form[Device](
    mapping(
      "ip" -> text,
      "deviceName" -> text
    ) (Device.apply)(Device.unapply)
  )
}
