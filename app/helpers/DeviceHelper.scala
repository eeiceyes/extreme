package helpers

object DeviceHelper {
  def translateType(t: Int) = {
    t match {
      case 1 => "交换机"
      case 2 => "路由器"
      case 3 => "交换路由器"
    }
  }
}
