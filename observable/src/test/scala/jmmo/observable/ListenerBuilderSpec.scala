package jmmo.observable

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

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
  val event21 = new Event21

  class Event3(val source: Observable = observable1) extends ObservableEvent
  val event3 = new Event3

  val handler1 = mock[(Event1) => Unit]
  val handler2 = mock[(Event2) => Unit]

  var builder: ListenerBuilder = _

  "A ListenerBuilder" should {

    "has companion object to create ListenerBuilder instance" in {
      builder = ListenerBuilder()
    }

    "provide addFilter method" which {

      "add observable filter to filter observable of some class" in {
        builder.addFilterWC[SomeObservable](_.name == "first")
      }

      "throws IllegalArgumentException if added filters for class that already has a filter" in {
        evaluating { builder.addFilterWC[SomeObservable]((_) => true)} should produce [IllegalArgumentException]
      }
    }

    "provide addHandler method" which {

      "add handler for events of some class" in {
        builder.addHandlerWC[Event1](handler1).addHandlerWC[Event2](handler2)
      }

      "throws IllegalArgumentException if added handler for class that already has a handler" in {
        evaluating { builder.addHandlerWC[Event1]((event) => {})} should produce [IllegalArgumentException]
      }
    }

    "provide level method" which {

      "set child observable nesting level for subscription" in {
        builder.setLevel(0)
      }

      "throws IllegalArgumentException if specified level is smaller than zero" in {
        evaluating { builder.setLevel(-1) } should produce [IllegalArgumentException]
      }
    }

    "provide build method to build ObservableListener" in {
      val listener = builder.build()

      observable1.addObservableListener(listener)
      observable2.addObservableListener(listener)
      observable3.addObservableListener(listener)

      observable1.publicFireObservableEvent(event1)
      verify(handler1).apply(event1)
      verifyZeroInteractions(handler2)

      reset(handler1, handler2)
      observable1.publicFireObservableEvent(event21)
      verifyZeroInteractions(handler1)
      verify(handler2).apply(event21)

      reset(handler1, handler2)
      observable1.publicFireObservableEvent(event3)
      verifyZeroInteractions(handler1)
      verifyZeroInteractions(handler2)

      observable2.publicFireObservableEvent(event1)
      verifyZeroInteractions(handler1)
      verifyZeroInteractions(handler2)

      reset(handler1, handler2)
      observable2.publicFireObservableEvent(event21)
      verifyZeroInteractions(handler1)
      verifyZeroInteractions(handler2)

      reset(handler1, handler2)
      observable2.publicFireObservableEvent(event3)
      verifyZeroInteractions(handler1)
      verifyZeroInteractions(handler2)

      observable3.publicFireObservableEvent(event1)
      verifyZeroInteractions(handler1)
      verifyZeroInteractions(handler2)

      reset(handler1, handler2)
      observable3.publicFireObservableEvent(event21)
      verifyZeroInteractions(handler1)
      verifyZeroInteractions(handler2)

      reset(handler1, handler2)
      observable3.publicFireObservableEvent(event3)
      verifyZeroInteractions(handler1)
      verifyZeroInteractions(handler2)
    }
  }
}
