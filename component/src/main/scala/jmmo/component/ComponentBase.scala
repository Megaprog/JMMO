package jmmo.component

import scala.reflect.ClassTag

/**
 * User: Tomas
 * Date: 12.09.13
 * Time: 10:48
 */
trait ComponentBase[A] extends Component[A] {
  protected var containerOption: Option[ComponentContainer] = None

  def componentIface: A = {
    this.asInstanceOf[A]
  }

  def handleIface[I](handler: (I) => Unit)(implicit tag: ClassTag[I]) {
    if (tag.runtimeClass.isInstance(componentIface)) {
      handler(componentIface.asInstanceOf[I])
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
    if (container.iface(componentType).isDefined) {
      onBecomeRevoked()
    }
  }

  protected def onBecomeRevoked() {
    container.becomeRevoked(this)
  }
}
