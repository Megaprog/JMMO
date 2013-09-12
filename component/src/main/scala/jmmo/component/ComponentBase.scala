package jmmo.component

import scala.reflect.ClassTag

/**
 * User: Tomas
 * Date: 12.09.13
 * Time: 10:48
 */
trait ComponentBase[A] extends Component[A] {
  protected var containerOption: Option[ComponentContainer] = None

  def componentInterface: A = {
    this.asInstanceOf[A]
  }

  def forInterface[I](handler: (I) => Unit)(implicit tag: ClassTag[I]) {
    if (tag.runtimeClass.isInstance(componentInterface)) {
      handler(componentInterface.asInstanceOf[I])
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
    containerOption foreach { _.becomeAvailable(this) }
  }

  def containerRevoked(container: ComponentContainer) {
    onContainerRevoked()
    this.containerOption = None
  }

  protected def onContainerRevoked() {
    containerOption foreach { (container) =>
      if (container.component(componentType).isDefined) {
        onBecomeRevoked()
      }
    }
  }

  protected def onBecomeRevoked() {
    containerOption foreach { _.becomeRevoked(this) }
  }
}
