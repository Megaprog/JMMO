package jmmo.observable

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

/**
 * User: Tomas
 * Date: 02.09.13
 * Time: 11:53
 */
class ObservableContainerSpec(creator: => PublicContainer[Observable]) extends WordSpec with ShouldMatchers with MockitoSugar {

  def this() = this(new ObservableContainerBase[Observable] with PublicContainer[Observable] {})

  override val nestedSuites = List(
    new ObservableFirerSpec(creator),
    new ObservableImmContainerSpec((children) => {
      val innerContainer = creator
      children foreach innerContainer.publicAddChildObservable
      innerContainer
    })
  )

  val container = creator
  val child = new ObservableBase with PublicFirer {}
  val event = new ObservableEvent { def source = null }

  val handler1 = mock[ObservableListener.Handler]
  val listener1 = ObservableListener(handler1)
  val handler2 = mock[ObservableListener.Handler]
  val listener2 = ObservableListener(handler2, (_, _) => false)

  "An Observable Container" should {

    "provide an addChildObservable method" which {

      "add a child observable to container" in {
        container.addObservableListener(listener1)
        container.addObservableListener(listener2)
        container.publicAddChildObservable(child)
      }

      "fires AddedObservableEvent" in {
        verify(handler1).apply(ObservableAddedEvent(container, child), Seq())
        verify(handler2, never()).apply(ObservableAddedEvent(container, child), Seq())
      }

      "subscribe observable to all listeners in container which pass filter and level" in {
        child.publicFireObservableEvent(event)
        verify(handler1).apply(event, Seq(container))
        verify(handler2, never()).apply(event, Seq(container))
      }
    }

    "provide childObservables method" which {

      "returns all child observables in container" in {
        var children = Vector[Observable]()
        container.publicChildObservables foreach (child => children = children :+ child)
        children should equal (Seq(child))
      }
    }

    "provide an removeChildObservable method" which {

      "remove a child observable to container" in {
        container.publicRemoveChildObservable(child)

        var children = Vector[Observable]()
        container.publicChildObservables foreach (child => children = children :+ child)
        children should be ('empty)
      }

      "fires RemovedObservableEvent" in {
        verify(handler1).apply(ObservableRemovedEvent(container, child), Seq())
        verify(handler2, never()).apply(ObservableRemovedEvent(container, child), Seq())
      }

      "unsubscribe observable from all listeners in container" in {
        reset(handler1, handler2)
        child.publicFireObservableEvent(event)
        verify(handler1, never()).apply(event, Seq(container))
        verify(handler2, never()).apply(event, Seq(container))
      }
    }
  }
}

trait PublicContainer[A <: Observable] extends ObservableContainer[A] with PublicFirer {
  def publicAddChildObservable(observable: A): Boolean = addChildObservable(observable)
  def publicRemoveChildObservable(observable: A): Boolean = removeChildObservable(observable)
  def publicChildObservables = childObservables
}