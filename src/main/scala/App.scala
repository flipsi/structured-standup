import ajax.AjaxTester
import com.raquo.laminar.api.L._
import org.scalajs.dom.document

object App {

  def main(args: Array[String]): Unit = {

    val teamMembers = List("Alex", "Armin", "Christof", "Luciano", "Nebo", "Philipp", "Sabrina")

    lazy val container = document.getElementById("app-container")

    lazy val appElement = div(
      h1("UES Daily Standup"),
      ul(
        fontSize := "130%",
        lineHeight := "2em",
        teamMembers.map { member =>
          li(
            span(member)
          )
        }
        ),
    )

    // Wait until the DOM is loaded, otherwise app-container element might not exist
    renderOnDomContentLoaded(container, appElement)
  }

}
