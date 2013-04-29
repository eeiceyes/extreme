package helpers

import collection.immutable.ListMap

/**
 * Date: 13-4-29
 */
object AlarmHelper {
  val ruleLevel = ListMap(1-> "低级", 2-> "中级", 3 -> "高级")
  val ruleDelay = ListMap(0-> 0, 1-> 1, 2 -> 2)
  val pollInterval = ListMap(10-> 10, 30-> 30, 60 -> 60, 120 -> 120, 180 -> 180, 300 -> 300)
  val validPeriod = ListMap(1-> "周一", 2-> "周二", 3-> "周三", 4-> "周四", 5-> "周五", 6-> "周六", 7-> "周日")
}
