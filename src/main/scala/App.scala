import scala.util.Random
import ajax.AjaxTester
import com.raquo.laminar.api.L._
import org.scalajs.dom.document

object App {

  val headline = "Structured Standup"

  // MODEL

  case class TeamMember(name: String)
  val teamMembers = List(
    TeamMember("Alex"),
    TeamMember("Armin"),
    TeamMember("Ashraf"),
    TeamMember("Christof"),
    TeamMember("Hanne"),
    TeamMember("Josef"),
    TeamMember("Luciano"),
    TeamMember("Nebo"),
    TeamMember("Philipp"),
  )

  def main(args: Array[String]): Unit = {

    // STATE

    implicit val random = new Random

    def getRandomElement[A](seq: Seq[A])(implicit random: Random): Option[A] =
      seq.length match {
        case 0 => None
        case n => Some(seq(random.nextInt(n)))
      }

    // Var-s are reactive state variables suitable for both local state and redux-like global stores.
    // Laminar uses my library Airstream as its reactive layer https://github.com/raquo/Airstream

    // val memberVar = Var(teamMembers.head)
    // val memberStream: EventStream[TeamMember] = ???
    // val memberSignal: Signal[TeamMember] = memberStream.startWith(teamMembers.head)
    // val memberSignal: Signal[TeamMember] = Signal.fromValue(teamMembers.head)

    val membersTodo: Var[List[TeamMember]] = Var(teamMembers)
    val membersDone: Var[List[TeamMember]] = Var(List.empty)
    val memberInSpotlight: Var[Option[TeamMember]] = Var(None)

    // TODO: refactor imperative implementation the reactive way
    def nominateNextMember(): Unit = {
      memberInSpotlight.now.map((last => membersDone.set((membersDone.now: List[TeamMember]) :+ last)))
      val theLuckyOne = getRandomElement(membersTodo.now)
      memberInSpotlight.set(theLuckyOne)
      theLuckyOne map { theOne =>
        println(s"Es freue sich: ${theOne.name}")
        membersTodo.set(membersTodo.now.filter(_ != theOne))
      }
    }

    // TODO: refactor imperative implementation the reactive way
    def nominateSpecificMember(member: TeamMember): Unit = {
      if (membersTodo.now.contains(member)) {
        memberInSpotlight.now.map((last => membersDone.set((membersDone.now: List[TeamMember]) :+ last)))
        memberInSpotlight.set(Some(member))
        println(s"Es freue sich: ${member.name}")
        membersTodo.set(membersTodo.now.filter(_ != member))
      } else if (membersDone.now.contains(member)) {
        memberInSpotlight.now.map((last => membersTodo.set((membersTodo.now: List[TeamMember]) :+ last)))
        memberInSpotlight.set(Some(member))
        println(s"Es freue sich: ${member.name}")
        membersDone.set(membersDone.now.filter(_ != member))
      }
    }

    // RENDERING

    def renderMember(member: TeamMember): Span =
      span(
        className("team-member"),

        member.name,
        onClick --> { _ => nominateSpecificMember(member) }
      )

    def renderMemberList(members: List[TeamMember]) =
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
        onClick --> { _ => nominateNextMember() }
      )

    lazy val appElement =
      div(
        className("app"),
        h1(headline),
        MemberList("Awaiting Spotlight:", "todo", membersTodo.signal),
        Spotlight(memberInSpotlight.signal),
        MemberList("Finished:", "done", membersDone.signal),
      )

    // Wait until the DOM is loaded, otherwise app-container element might not exist
    lazy val container = document.getElementById("app-container")
    renderOnDomContentLoaded(container, appElement)
  }

}
