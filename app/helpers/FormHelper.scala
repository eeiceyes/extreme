package helpers

import models._
import play.api.templates.Html
import views.html.share.form

class Form {
  def text_field(args: (Symbol, String)*) = {
    form.text_field(args.toMap)
  }

  def check_field(args: (Symbol, String)*) = {
    form.check_field(args.toMap)
  }

  def area_field(args: (Symbol, String)*) = {
    form.area_field(args.toMap)
  }

  def select_field(args: (Symbol, Any)*) = {
    form.select_field(args.toMap)
  }

  def map(action:String, method:String="POST", cls:String = "")(content: Html)= {
    form.form(action, method, cls){
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
