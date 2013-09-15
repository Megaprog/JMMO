package jmmo.component

import scala.reflect.ClassTag
import jmmo.observable.ObservableFirer

/**
 * User: Tomas
 * Date: 12.09.13
 * Time: 10:48
 */
trait ComponentBase[A] extends Component[A] with ObservableFirer {
  protected var containerOption: Option[ComponentContainer] = None

  def forPrimary[U](handler: A => U): U = {
    handler(this.asInstanceOf[A])
  }

  def forSecondary[I, U](handler: I => U)(implicit tagI: ClassTag[I]) {
    if (tagI.runtimeClass.isInstance(this)) {
      handler(this.asInstanceOf[I])
    }
  }

  protected def isAvailable: Boolean = {
    containerOption.isDefined
  }

  protected def container: ComponentContainer = {
    containerOption.get
  }

  def containerAvailable(container: ComponentContainer) {
    this.containerOption = Some(container)
    onContainerAvailable()
  }

  protected def onContainerAvailable() {
    onBecomeAvailable()
  }

  protected def onBecomeAvailable() {
    container.becomeAvailable(this)
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
    container.becomeRevoked(this)
  }
}
