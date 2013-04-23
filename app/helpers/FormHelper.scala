package helpers

import models._
import play.api.templates.Html
import views.html.share.form

class Form {
  def text_field(args: (Symbol, String)*) = {
    form.text_field(args.toMap)
  }

  def map(action:String, method:String="POST")(content: Html)= {
    form.form(action, method){
      content
    }
  }

  def fields(label: String)(content: Html)= {
    form.fieldset(label){
      content
    }
  }

  def actions(content: Html)= {
    form.actions(content)
  }
}

object FormHelper {
  def setup[T <: Getter](form_for: Form => Html):Html = {
    form_for(new Form())
  }
}
