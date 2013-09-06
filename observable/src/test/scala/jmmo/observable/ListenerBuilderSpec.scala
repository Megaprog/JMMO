package jmmo.observable

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar

/**
 * User: Tomas
 * Date: 06.09.13
 * Time: 22:00
 */
class ListenerBuilderSpec extends WordSpec with ShouldMatchers with MockitoSugar {

  class SomeObservable(val name: String) extends ObservableBase with PublicFirer {}
  val observable1 = new SomeObservable("first")
  val observable2 = new SomeObservable("second")
  val observable3 = new ObservableBase with PublicFirer {}

  class Event1(val source: Observable = observable1) extends ObservableEvent
  val event1 = new Event1

  class Event2(val source: Observable = observable1) extends ObservableEvent
  class Event21 extends Event2
  val event2 = new Event21

  class Event3(val source: Observable = observable1) extends ObservableEvent
  val event3 = new Event3

  var builder: ListenerBuilder = _

  "A ListenerBuilder" should {

    "has companion object to create ListenerBuilder instance" in {
      builder = ListenerBuilder()
    }

    "provide addFilter method to filter observable to which listeners added" in {
      builder.addFilterWC[SomeObservable](_.name == "first")
    }

    "provide addHandler method to handle events in listeners" in {
      val handler1 = mock[(Event1) => Unit]
      val handler2 = mock[(Event2) => Unit]
      builder.addHandlerWC[Event1](handler1).addHandlerWC[Event2](handler2)
    }

    "provide level method to set child observable nesting level for subscription" in {
      builder.setLevel(0)
    }

    "provide build method to build ObservableListener" in {
      val listener = builder.build()
    }
  }
}
