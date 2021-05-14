import ajax.AjaxTester
import com.raquo.laminar.api.L._
import org.scalajs.dom.document

object App {

  def main(args: Array[String]): Unit = {

    // MODEL

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

    // STATE

    // Var-s are reactive state variables suitable for both local state and redux-like global stores.
    // Laminar uses my library Airstream as its reactive layer https://github.com/raquo/Airstream

    // val memberVar = Var(teamMembers.head)
    // val memberStream: EventStream[TeamMember] = ???
    // val memberSignal: Signal[TeamMember] = memberStream.startWith(teamMembers.head)
    // val memberSignal: Signal[TeamMember] = Signal.fromValue(teamMembers.head)

    val membersTodo: Var[List[TeamMember]] = Var(teamMembers)
    val membersDone: Var[List[TeamMember]] = Var(List.empty)
    val memberinSpotlight: Var[Option[TeamMember]] = Var(None)


    // RENDERING

    def renderMember(member: TeamMember): Span =
      span(
        className("team-member"),
        span(className("member-icon", "material-icons"), "person"),
        member.name
      )

    def renderMemberList(members: List[TeamMember])  =
      ul(
        className("team-members"),
        members.map { member =>
          li(
            renderMember(member)
          )
        }
      )

    def MemberList(title: String, cls: String, $members: Signal[List[TeamMember]]) =
      div(
        className("team-members-container", cls),
        span(title),
        child <-- $members.map(renderMemberList)
      )

    def Spotlight($member: Signal[Option[TeamMember]]) =
      div(
        div(
          className("spotlight"),
          child <-- $member.map(x => if (x.isDefined) renderMember(x.get) else emptyNode)
        ),
        onClick --> { _ => println("Coooool") }
      )

    lazy val appElement =
      div(
        className("app"),
        h1("UES Daily Standup"),
        MemberList("Awaiting Spotlight:", "todo", membersTodo.signal),
        Spotlight(memberinSpotlight.signal),
        MemberList("Finished:", "done", membersDone.signal),
      )

    // Wait until the DOM is loaded, otherwise app-container element might not exist
    lazy val container = document.getElementById("app-container")
    renderOnDomContentLoaded(container, appElement)
  }

}
