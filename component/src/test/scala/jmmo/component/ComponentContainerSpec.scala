package jmmo.component

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import jmmo.component.example.{SlaveComponent, MasterComponent, CountableComponent}
import language.existentials
import org.mockito.Mockito._
import java.lang.IllegalArgumentException
import scala.reflect.ClassTag

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

    "provide `addComponent` method to add a component to container" in {
      container.addComponent(someComponent)
      container.components should be (Set(someType))

      info("`components` methods returns all components regardless them available or not")
      container.addComponent(slaveComponent)
      container.components should be (Set(someType, slaveType))
      container.isComponentAvailable(slaveType) should be (false)

      info("re-adding the same component will produce Exception")
      evaluating {container.addComponent(slaveComponent)} should produce[IllegalArgumentException]
    }

    "provide `removeComponent` method to remove a component from container" in {
      container.removeComponent(someType)
      container.removeComponent(slaveType)
      container.components should be ('empty)

      info("removing the component which is not exists in container will produce Exception")
      evaluating {container.removeComponent(someType)} should produce[IllegalArgumentException]
    }

    "provide `isComponentAvailable` method to check component availability" in {
      container.addComponent(someComponent)
      container.isComponentAvailable(someType) should be (true)

      container.addComponent(slaveComponent)
      container.isComponentAvailable(slaveType) should be (false)

      container.addComponent(masterComponent)
      container.isComponentAvailable(masterType) should be (true)
      container.isComponentAvailable(slaveType) should be (true)

      container.removeComponent(someType)
      container.isComponentAvailable(someType) should be (false)

      container.removeComponent(masterType)
      container.isComponentAvailable(masterType) should be (false)
      container.isComponentAvailable(slaveType) should be (false)

      container.removeComponent(slaveType)
      container.isComponentAvailable(slaveType) should be (false)
    }

    "provide `forPrimary` method to handle the primary component interface of specified class" in {
      container.addComponent(someComponent)

      var flag = false
      container.forPrimary[Any, Unit](_ => flag = true)(ClassTag(someType)).isDefined should be (true)
      flag should be (true)

      flag = false
      container.forPrimary[Any, Unit](_ => flag = true)(ClassTag(masterType)).isDefined should be (false)
      flag should be (false)

      container.addComponent(masterComponent)
      container.forPrimary[Any, Unit](_ => flag = true)(ClassTag(masterType)).isDefined should be (true)
      flag should be (true)

      flag = false
      container.forPrimary[Any, Unit](_ => flag = true)(ClassTag(slaveType)).isDefined should be (false)
      flag should be (false)

      container.addComponent(slaveComponent)
      container.forPrimary[Any, Unit](_ => flag = true)(ClassTag(slaveType)).isDefined should be (true)
      flag should be (true)

      container.removeComponent(masterType)
      flag = false
      container.forPrimary[Any, Unit](_ => flag = true)(ClassTag(slaveType)).isDefined should be (false)
      flag should be (false)
    }

    "provide `forSecondary` method to handle some interfaces supported by any available components in container" in {

    }
  }
}