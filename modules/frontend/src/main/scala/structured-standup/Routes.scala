package structuredStandup

import com.raquo.laminar.api.L._
import io.frontroute._
import org.scalajs.dom.document
import structuredStandup.pages._

import scala.concurrent.duration._
import scala.scalajs.js.timers.setInterval

object Routes {

  private val (routeResult, route) = makeRoute[Element] { render =>
    concat(
      param("users") { users =>
        render { StandupPage(users) }
      },
      render { IndexPage() }
    )
  }

  private val currentRender = routeResult.map(_.getOrElse(div("initializing...")))

  def start(): Unit = {
    val container = document.getElementById("app-container")
    runRoute(route, LocationProvider.browser(windowEvents.onPopState))(unsafeWindowOwner)
    BrowserNavigation.emitPopStateEvent()
    val _ = render(container, div(child <-- currentRender))
  }
}
