package jmmo.component.impl

import scala.reflect.ClassTag
import jmmo.observable.ObservableFirer
import jmmo.component.{ComponentRevoked, ComponentAvailable, ComponentContainer, Component}

/**
 * User: Tomas
 * Date: 12.09.13
 * Time: 10:48
 */
trait ComponentGen[A] extends Component[A] with ObservableFirer {
  protected var containerOption: Option[ComponentContainer] = None

  protected def container: ComponentContainer = {
    containerOption.get
  }

  def forPrimary[U](handler: A => U): U = {
    handler(this.asInstanceOf[A])
  }

  def forSecondary[I, U](handler: I => U)(implicit tagI: ClassTag[I]) {
    if (tagI.runtimeClass.isInstance(this)) {
      handler(this.asInstanceOf[I])
    }
  }

  def containerAvailable(container: ComponentContainer) {
    this.containerOption = Some(container)
    onContainerAvailable()
  }

  protected def onContainerAvailable() {
    onBecomeAvailable()
  }

  protected def onBecomeAvailable() {
    fireObservableEvent(ComponentAvailable(this))
  }

  def containerRevoked(container: ComponentContainer) {
    onContainerRevoked()
    this.containerOption = None
  }

  protected def onContainerRevoked() {
    if (container.isComponentAvailable(componentType)) {
      onBecomeRevoked()
    }
  }

  protected def onBecomeRevoked() {
    fireObservableEvent(ComponentRevoked(this))
  }
}
