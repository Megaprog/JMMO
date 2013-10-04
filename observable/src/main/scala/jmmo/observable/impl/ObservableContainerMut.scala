package jmmo.observable.impl

import jmmo.observable._
import jmmo.observable.ObservableAddedEvent

/**
 * User: Tomas
 * Date: 31.08.13
 * Time: 23:21
 */
trait ObservableContainerMut[A <: Observable] extends ObservableContainer[A]
    with ObservableContainerImm[A] with ChildListenersSupport with ChildElementsSupport[A] {

  protected def addChildElement(observable: A): Boolean = {
    if (childElementsAdd(observable)) {
      onAddChildObservable(observable)
      true
    }
    else {
      false
    }
  }

  protected def removeChildElement(observable: A): Boolean = {
    if (childElementsRemove(observable)) {
      onRemoveChildObservable(observable)
      true
    }
    else {
      false
    }
  }

  protected def onAddChildObservable(observable: A) {
    doFireAddedObservableEvent(this, observable)

    //subscribe child
    childListeners foreach observable.addObservableListener
  }

  protected def onRemoveChildObservable(observable: A) {
    //unsubscribe child
    childListeners foreach observable.removeObservableListener

    doFireRemovedObservableEvent(this, observable)
  }

  protected def doFireAddedObservableEvent(source: Observable, participant: Observable) {
    fireObservableEvent(ObservableAddedEvent(source, participant))
  }

  protected def doFireRemovedObservableEvent(source: Observable, participant: Observable) {
    fireObservableEvent(ObservableRemovedEvent(source, participant))
  }

  override protected def addChildListenerWrapper(listenerWrapper: ObservableListener) {
    if (childListenersContains(listenerWrapper)) {
      throw new IllegalArgumentException(s"Observable listenerWrapper $listenerWrapper already exists in $this")
    }

    childListenersAdd(listenerWrapper)

    super.addChildListenerWrapper(listenerWrapper)
  }

  override protected def removeChildListenerWrapper(listenerWrapper: ObservableListener) {
    childListenersRemove(listenerWrapper)

    super.removeChildListenerWrapper(listenerWrapper)
  }
}
