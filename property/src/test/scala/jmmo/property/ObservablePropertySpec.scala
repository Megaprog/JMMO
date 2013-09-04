package jmmo.property

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import jmmo.observable.{ObservableListener, PublicFirer, ObservableFirerSpec}
import org.mockito.Mockito._

/**
 * User: Tomas
 * Date: 04.09.13
 * Time: 15:12
 */
class ObservablePropertySpec(creator: (String) => ObservableProperty[Int] with PublicFirer)  extends WordSpec with ShouldMatchers with MockitoSugar {

  def this() = this((name) => new PropertyImpl[Int](name) with PublicFirer)

  val Name = "TestProp"

  override val nestedSuites = List(
    new ObservableFirerSpec(creator(Name))
  )

  val property = creator(Name)

  "An Observable Property" should {

    "provide a `name` method" which {

      "returns the property name" in {
        property.name should equal(Name)
      }
    }

    "provide an `update` method" which {

      "updates property value" in {
        property() = 1
        property() should equal(1)
      }

      "fires ChangedValueEvent" in {
        val listener = ObservableListener(mock[ObservableListener.Handler])
        property.addObservableListener(listener)

        property() = 3
        verify(listener.handler).apply(ChangedValueEvent(property, 1, 3), Seq())
      }
    }

    "provide an apply method" which {

      "returns property value" in {
        property() should equal(3)
      }
    }
  }
}