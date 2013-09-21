package jmmo.component

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import jmmo.component.items.{Rechargeable, RechargeableComponent}
import java.util.concurrent.Callable

/**
 * User: Tomas
 * Date: 19.09.13
 * Time: 13:04
 */
class ComponentSpec extends WordSpec with ShouldMatchers {

  val component = new RechargeableComponent

  "A Component" should {

    "provide a componentType method to returns Class of component interface" in {
      component.componentType should be (classOf[Rechargeable])
    }

    "provide a forPrimary method to handle the primary component interface" in {
      var flag = false

      (component.forPrimary { (rechargeable) =>
        flag = true
        rechargeable.getQuantityInCartridge
      }) should be (1)

      flag should be (true)
    }

    "provide a forSecondary method to handle supported by component secondary interfaces" in {
      info("Runnable is supported and handled")
      var runnableFlag = false

      component.forSecondary { (runnable: Runnable) =>
        runnableFlag = true
      }

      runnableFlag should be (true)

      info("Callable is not supported and not handled")
      var callableFlag = false

      component.forSecondary { (callable: Callable[_]) =>
        callableFlag = true
      }

      callableFlag should be (false)
    }
  }
}