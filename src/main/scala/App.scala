import ajax.AjaxTester
import com.raquo.laminar.api.L._
import org.scalajs.dom.document

object App {

  def main(args: Array[String]): Unit = {

    case class TeamMember(name: String)

    val teamMembers = List(
      TeamMember("Alex"),
      TeamMember("Armin"),
      TeamMember("Ashraf"),
      TeamMember("Christof"),
      TeamMember("Luciano"),
      TeamMember("Nebo"),
      TeamMember("Philipp"),
      TeamMember("Sabrina")
    )

    // val memberStream: Signal[TeamMember] = ???

    def render(member: TeamMember): Span =
      span(
        span(className(List("member-icon", "material-icons")), "person"),
        member.name
      )

    // val colorStream: EventStream[String]

    // def spotlightMemberView($members: Signal[TeamMember]): Div = div($members.map(_.name))

    lazy val spotlight =
      div(
        div(
          className(List("spotlight")),
          // color <-- colorStream
        ),
        onClick --> { _ => println("Coooool") }
      )

    lazy val appElement =
      div(
        className("app"),
        h1("UES Daily Standup"),
        spotlight,
        div(
          className("team-members-container"),
          ul(
            className("team-members"),
            teamMembers.map { member =>
              li(
                className("team-member"),
                render(member)
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
