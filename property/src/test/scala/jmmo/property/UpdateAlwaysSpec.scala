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
class UpdateAlwaysSpec(creator: (String) => UpdateAlways[Int] with PublicFirer)  extends WordSpec with ShouldMatchers with MockitoSugar {

  def this() = this((name) => new PropertyAlways[Int](name) with PublicFirer)

  val Name = "TestAlways"

  override val nestedSuites = Vector(
    new ObservableFirerSpec(creator(Name)),
    new ObservablePropertySpec(creator)
  )

  val property = creator(Name)

  "A Property which updates it value and fire ChangedValueEvent in spite of new value equals old one" should {

    "provide an `update` method" which {

      "updates property value" in {
        property() = 1
        property() should equal(1)
      }

      "fires ChangedValueEvent in spite of new value equals old one" in {
        val listener = ObservableListener(mock[ObservableListener.Handler])
        property.addObservableListener(listener)

        property() = 1
        verify(listener.handler).apply(ChangedValueEvent(property, 1, 1), Seq())
      }
    }
  }
}