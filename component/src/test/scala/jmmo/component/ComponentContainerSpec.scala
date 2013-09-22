package jmmo.component

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import jmmo.component.example.{SlaveComponent, MasterComponent, CountableComponent}
import language.existentials
import org.mockito.Mockito._

/**
 * User: Tomas
 * Date: 19.09.13
 * Time: 13:05
 */
class ComponentContainerSpec(creator: => (ComponentContainer, (Class[_], Component[_]), (Class[_], Component[_]), (Class[_], DepComponent[_])))
  extends WordSpec with ShouldMatchers with MockitoSugar {

  def this() = this(
    new ComponentContainerBase {},
    {val someComponent = new CountableComponent; (someComponent.componentType, someComponent)},
    {val masterComponent = new MasterComponent; (masterComponent.componentType, masterComponent)},
    {val slaveComponent = new SlaveComponent; (slaveComponent.componentType, slaveComponent)}
  )

  val (container, (someType, someComponent), (masterType, masterComponent), (slaveType, slaveComponent)) = creator

  "A ComponentContainer" should {

    "provide `components` method which empty on container creation" in {
      container.components should be ('empty)
    }

    "provide 'addComponent` method to add a component to container" in {
      container.addComponent(someComponent)
      container.components should be (Set(someType))

      info("`components` methods returns all components regardless them available or not")
      container.addComponent(slaveComponent)
      container.components should be (Set(someType, slaveType))
      container.isComponentAvailable(slaveType) should be (false)
    }

    "provide `removeComponent` method to remove a component from container" in {
      container.removeComponent(someType)
      container.removeComponent(slaveType)
      container.components should be ('empty)
    }
  }
}