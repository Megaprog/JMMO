package jmmo.observable

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

/**
 * User: Tomas
 * Date: 02.09.13
 * Time: 10:01
 */
class ObservableImmContainerSpec(creator: (TraversableOnce[Observable]) => Observable) extends WordSpec with ShouldMatchers with MockitoSugar {

  def this() = this((children) => new ObservableImmContainerBase[Observable] { def childElements = children })

  val child1 = new ObservableBase with PublicFirer {}
  val child2 = new ObservableBase with PublicFirer {}
  val container = creator(List(child1, child2))

  val event1 = new ObservableEvent { def source = null }
  val event2 = new ObservableEvent { def source = null }

  "An Observable Immutable Container" should {

    val handler1 = mock[ObservableListener.Handler]
    val listener1 = ObservableListener(handler1)

    "provide an addObservableListener method" which {

      "add a listener to children who pass a filter and level" in {
        container.addObservableListener(listener1)

        info("all events fires in children has `container` in chain")

        child1.publicFireObservableEvent(event1)
        verify(handler1).apply(event1, Seq(container))

        child2.publicFireObservableEvent(event2)
        verify(handler1).apply(event2, Seq(container))
      }

      "not add a listener to children who not pass a filter" in {
        val handler = mock[ObservableListener.Handler]
        container.addObservableListener(ObservableListener(handler, (observable, chain) => {
          if (observable == child1) {
            info("for children filter `container` must be in chain")
            chain should equal (Seq(container))
            true
          }
          else {
            false
          }
        }))

        child1.publicFireObservableEvent(event1)
        verify(handler).apply(event1, Seq(container))

        child2.publicFireObservableEvent(event2)
        verify(handler, never()).apply(event2, Seq(container))
      }

      "not add a listener to children if level is zero (ParentLevel)" in {
        val handler = mock[ObservableListener.Handler]
        container.addObservableListener(ObservableListener(handler, level = ObservableListener.ParentLevel))

        child1.publicFireObservableEvent(event1)
        verify(handler, never()).apply(event1, Seq(container))

        child2.publicFireObservableEvent(event2)
        verify(handler, never()).apply(event2, Seq(container))
      }
    }

    "provide an removeObservableListener method" which {

      "remove a listener from children" in {
        reset(handler1)
        container.removeObservableListener(listener1)

        child1.publicFireObservableEvent(event1)
        verify(handler1, never()).apply(event1, Seq(container))

        child2.publicFireObservableEvent(event2)
        verify(handler1, never()).apply(event2, Seq(container))
      }
    }
  }
}