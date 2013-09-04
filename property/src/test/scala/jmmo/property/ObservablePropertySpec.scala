package jmmo.property

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar

/**
 * User: Tomas
 * Date: 04.09.13
 * Time: 15:12
 */
class ObservablePropertySpec(creator: (String) => ObservableProperty[Int])  extends WordSpec with ShouldMatchers with MockitoSugar {

  def this() = this((name) => PropertyImpl[Int](name))
}
