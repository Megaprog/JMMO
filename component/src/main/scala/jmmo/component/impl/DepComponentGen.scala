package jmmo.component.impl

import jmmo.observable.{ObservableRemovedEvent, ObservableAddedEvent, ObservableListener}
import jmmo.component.{Component, DepComponent}

/**
 * User: Tomas
 * Date: 21.09.13
 * Time: 22:06
 */
trait DepComponentGen[A] extends DepComponent[A] with ComponentGen[A] {

  protected def isAvailable: Boolean = {
    containerOption.exists(container => require.forall(container.isComponentAvailable))
  }

  override protected def onContainerAvailable() {
    container.addObservableListener(containerListener)

    if (isAvailable) {
      super.onContainerAvailable()
    }
  }

  override protected def onContainerRevoked() {
    container.removeObservableListener(containerListener)

    super.onContainerRevoked()
  }

  protected val containerListener = ObservableListener( (event, _) => event match {
    case ObservableAddedEvent(_, component: Component[_]) if require(component.componentType) && isAvailable =>
      onBecomeAvailable()

    case ObservableRemovedEvent(_, component: Component[_]) if require(component.componentType) =>
      onBecomeRevoked()

  }, level = ObservableListener.ParentLevel)
}
