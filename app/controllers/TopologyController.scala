package controllers

import play.api.mvc.{Action, Controller}

object TopologyController extends Controller {

  def view = Action {
    Ok(views.html.topology.display(1))
  }
}
