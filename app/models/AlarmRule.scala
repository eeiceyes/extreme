package models

/**
 * Date: 13-4-23
 */
case class AlarmRule(status:Int, name:String, isSystem: Boolean, target:String, level: Int, notificationType:String, createdTime: String)  extends Getter {
  var id: Long = _
}
