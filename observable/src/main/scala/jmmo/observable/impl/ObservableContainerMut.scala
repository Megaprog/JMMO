package jmmo.observable.impl

import jmmo.observable._
import jmmo.observable.AddedObservableEvent

/**
 * User: Tomas
 * Date: 31.08.13
 * Time: 23:21
 */
trait ObservableContainerMut[A <: Observable] extends ObservableContainer[A]
    with ObservableContainerImm[A] with ChildListenersSupport with ChildObservablesSupport[A] {

  protected def addChildObservable(observable: A): Boolean = {
    if (childObservablesAdd(observable)) {
      onAddChildObservable(observable)
      true
    }
    else {
      false
    }
  }

  protected def removeChildObservable(observable: A): Boolean = {
    if (childObservablesRemove(observable)) {
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
    fireObservableEvent(AddedObservableEvent(source, participant))
  }

  protected def doFireRemovedObservableEvent(source: Observable, participant: Observable) {
    fireObservableEvent(RemovedObservableEvent(source, participant))
  }

  override protected def addChildListenerWrapper(listenerWrapper: ObservableListener) {
    if (childListenersExists(listenerWrapper)) {
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
