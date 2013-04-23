package helpers

import play.api.templates.Html
import views.html.share.grid
import models._
import collection.mutable
import play.api.mvc.Request

class Grid[T <: models.Getter](data: List[T], baseUrl: String) {
  private var columnHtml: Html = Html.empty
  private var tableHtml: Html = Html.empty
  private var cln2Renderer: mutable.LinkedHashMap[String, (T) => String] = mutable.LinkedHashMap()
  var condition: String = _
  var sortTo: String = _
  var sortBy: String = _
  var pageIndex: Int = 1
  var pageSize: Int = 100
  var dataCount: Int = 100

  def table(content: Html) = {
    val t = {
      var rowsHtml = Html.empty
      data.foreach(record => {
        var rowString = ""
        cln2Renderer.keys.foreach(key => {
          val ref = cln2Renderer.get(key).get
          rowString += "<td>" + ref.apply(record) + "</td>"
        })
        rowsHtml += Html.apply("<tr>" + rowString + "</tr>")
      })
      rowsHtml
    }
    tableHtml += grid.table(content) (t) {
      grid.pagination(pageIndex, Math.ceil(dataCount*1.0 / pageSize).toInt, pageBaseUrl)
    }
  }

  def column(label: String, id: String, sortable: Boolean, renderer: T => String) = {
    cln2Renderer += (id -> renderer)
    columnHtml += grid.column(label, headerUrl(id))
  }

  def column(label: String, id: String, renderer: T => String):Html = {
    column(label, id, sortable = false, renderer)
  }

  def column(label: String, id: String, sortable: Boolean) = {
    cln2Renderer += (id -> ((x: T) => {
      x.get(id)
    }))
    columnHtml += grid.column(label, headerUrl(id))
  }

  def column(label: String, id: String):Html = {
    column(label, id, sortable = false)
  }

  def check_column(id: String) {
    cln2Renderer += (id -> ((x: T) => {
      s"<input type='checkbox' class='row-checker' key='${x.get(id)}' />"
    }))
    columnHtml += grid.check_column()
  }

  private def headerUrl(current: String)= {
    val sort_by = current
    var sort_to = "desc"
    if(!sortTo.isEmpty && current == sortBy) {
        sort_to = if(sortTo == "desc") "asc" else "desc"
    }

    s"$baseUrl?sort_by=$sort_by&sort_to=$sort_to&page_index=$pageIndex&page_size=$pageSize&condition=$condition"
  }

  private def pageBaseUrl= {
    s"$baseUrl?sort_by=$sortBy&sort_to=$sortTo&page_size=$pageSize&condition=$condition"
  }
}

object GridHelper {
  val grid: ThreadLocal[AnyRef] = new ThreadLocal[AnyRef]()

  def setup[T <: Getter](grid_for: Grid[T] => Html) = {
    grid_for(grid.get().asInstanceOf[Grid[T]])
  }

  def configure[T <: Getter](request: Request[AnyRef], baseUrl:String, data: List[T], dataCount: Int) {
    val sortBy = request.getQueryString("sort_by").getOrElse("")
    val sortTo = request.getQueryString("sort_to").getOrElse("")
    val pageIndex = request.getQueryString("page_index").getOrElse("1")
    val pageSize = request.getQueryString("page_size").getOrElse("100")
    val condition = request.getQueryString("condition").getOrElse("")

    val g = new Grid[T](data, baseUrl)
    g.sortBy = sortBy
    g.sortTo = sortTo
    g.pageIndex = Integer.parseInt(pageIndex)
    g.pageSize = Integer.parseInt(pageSize)
    g.dataCount = dataCount
    grid.set(g)

  }
}
