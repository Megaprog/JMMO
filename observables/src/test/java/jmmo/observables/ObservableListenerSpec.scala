package jmmo.observables

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

      class MyEvent(source: AnyRef) extends ObservableEvent(source)

      val classes = Set[ObservableListener.EventClass](classOf[MyEvent], classOf[ObservableEvent])

      val listener = ObservableListener(handler, filter, level, classes)

      listener.handler should be theSameInstanceAs(handler)
      listener.filter should be theSameInstanceAs(filter)
      listener.classes should be theSameInstanceAs(classes)
      listener.level should equal(level)
    }

    "have unapply method for pattern matching" in {
      val handler = (_: ObservableEvent, _: Seq[Observable]) => {}
      val filter = (_: Observable, _: Seq[Observable]) => true
      val level = 1
      val classes = Set[ObservableListener.EventClass](classOf[ObservableEvent])

      val listener = ObservableListener(handler, filter, level, classes)

      (listener match {
        case ObservableListener(h, f, l, c) if (h eq handler) && (f eq filter) && (c eq classes) && (l == level) => true
        case _ => false
      }) should be (true)
    }

    "have implicit conversion from (ObservableEvent, Seq[Observable]) => Unit to ObservableListener" in {
      val handler = (_: ObservableEvent, _: Seq[Observable]) => {}

      import ObservableListener.ImplicitObservableListener

      handler.handler should be theSameInstanceAs(handler)
      handler.filter should be theSameInstanceAs(ObservableListener.PassAll)
      handler.classes should be theSameInstanceAs(ObservableListener.AllClasses)
      handler.level should equal(ObservableListener.MaxLevel)
    }
  }
}
