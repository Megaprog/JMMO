package jmmo.property

import org.scalatest.WordSpec

/**
 * User: Tomas
 * Date: 04.09.13
 * Time: 15:13
 */
class PropertySuite extends WordSpec {

  override val nestedSuites = Vector(
    new ObservablePropertySpec
  )

  "Observables" should {

    "fit all specifications in package" in {}

  }
}
