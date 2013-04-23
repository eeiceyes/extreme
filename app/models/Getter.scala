package models

trait Getter {
  def get(key:String)= {
    val field = this.getClass.getDeclaredField(key)
    field.setAccessible(true)
    val vl = field.get(this)
    if (vl != null) vl.toString else ""
  }
}
