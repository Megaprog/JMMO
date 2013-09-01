package jmmo.observable

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * User: Tomas
 * Date: 08.08.13
 * Time: 14:01
 */
class ObservableListenerSpec extends WordSpec with ShouldMatchers {

  "An ObservableListener" should {

    "have apply method to create ObservableListener instance" in {
      val handler = (_: ObservableEvent, _: Seq[Observable]) => {}
      val filter = (_: Observable, _: Seq[Observable]) => true
      val level = 1

      class MyEvent(val source: AnyRef) extends ObservableEvent

      val listener = ObservableListener(handler, filter, level)

      listener.handler should be theSameInstanceAs(handler)
      listener.filter should be theSameInstanceAs(filter)
      listener.level should equal(level)
    }

    "have unapply method for pattern matching" in {
      val handler = (_: ObservableEvent, _: Seq[Observable]) => {}
      val filter = (_: Observable, _: Seq[Observable]) => true
      val level = 1

      val listener = ObservableListener(handler, filter, level)

      (listener match {
        case ObservableListener(h, f, l) if (h eq handler) && (f eq filter) && (l == level) => true
        case _ => false
      }) should be (true)
    }

    "have implicit conversion from (ObservableEvent, Seq[Observable]) => Unit to ObservableListener" in {
      val handler = (_: ObservableEvent, _: Seq[Observable]) => {}

      import ObservableListener.ImplicitObservableListener

      handler.handler should be theSameInstanceAs(handler)
      handler.filter should be theSameInstanceAs(ObservableListener.PassAll)
      handler.level should equal(ObservableListener.MaxLevel)
    }
  }
}
