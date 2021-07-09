package structuredStandup.pages

import com.raquo.laminar.api.L._
import org.scalajs.dom.document

import scala.scalajs.js.JSON

object IndexPage {

  val headline = "Structured Standup"

  val memberInput: Var[String] = Var("")

  def apply(): HtmlElement = {
    div(
      className("app"),
      h1(headline),
      form(
        p(
          label("Team Members: "),
          input(
            onMountFocus,
            name := "users",
            placeholder := "comma separated names",
            onInput.mapToValue --> memberInput
          ),
        ),
        button(typ("submit"), "Start Standup")
      )
    )
  }

}
