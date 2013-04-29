package helpers

import play.api.templates.Html


/**
 * Date: 13-4-24
 */
object LayoutHelper {

  def wrap(cls: String)(content: Html) = {
    Html.apply(s"<div class='$cls'> ") += content += Html.apply("</div>")
  }
}
