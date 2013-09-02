package jmmo.observable

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar

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
}

trait PublicContainer[A <: Observable] extends ObservableContainer[A] with PublicFirer {
  def publicAddChildObservable(observable: A): Boolean = addChildObservable(observable)
  def publicRemoveChildObservable(observable: A): Boolean = removeChildObservable(observable)
  def publicChildObservables: TraversableOnce[A] = childObservables
}