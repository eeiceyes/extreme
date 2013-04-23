package lib

import filters.LoginFilter
import play.api._
import play.api.mvc._

object Global extends GlobalSettings {
  override def doFilter(action: EssentialAction) = LoginFilter(action)

  override def onHandlerNotFound(request: RequestHeader): Result = {
    Logger.error(s"Resource${request.path} not found")
    Results.Ok(
      views.html.errors.not_found(request.path)
    )
  }

  override def onError(request: RequestHeader, ex: Throwable) = {
    Logger.error("Server exception happened", ex)
    Results.Ok(
      views.html.errors.server_error(ex)
    )
  }

//  override def onRouteRequest(request: RequestHeader): Option[Handler] = {
//    request.headers.get("X-HTTP-Method-Override") match {
//      case Some(method) => super.onRouteRequest(new RequestHeader() {
//        def id = request.id
//        def tags = request.tags
//        def headers = request.headers
//        def queryString = request.queryString
//        def path = request.path
//        def uri = request.uri
//        def method = method
//        def version = request.version
//        def remoteAddress = request.remoteAddress
//      })
//      case None => super.onRouteRequest(request)
//    }
//  }
}
