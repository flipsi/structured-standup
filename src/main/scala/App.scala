import ajax.AjaxTester
import com.raquo.laminar.api.L._
import org.scalajs.dom.document

object App {

  def main(args: Array[String]): Unit = {

    val teamMembers = List(
      "Alex",
      "Armin",
      "Ashraf",
      "Christof",
      "Luciano",
      "Nebo",
      "Philipp",
      "Sabrina"
    )

    lazy val spotlight = div(className := "spotlight")

    lazy val appElement =
      div(
        className := "app",
        h1("UES Daily Standup"),
        spotlight,
        div(
          className := "team-members-container",
          ul(
            className := "team-members",
            teamMembers.map { member =>
              li(
                className := "team-member",
                span(member)
              )
            }
          )
        )
      )

    // Wait until the DOM is loaded, otherwise app-container element might not exist
    lazy val container = document.getElementById("app-container")
    renderOnDomContentLoaded(container, appElement)
  }

}
