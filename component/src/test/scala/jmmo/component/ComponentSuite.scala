/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.component

import org.scalatest.WordSpec
import jmmo.observable.impl.ListenerWrapperSpec

/**
 * User: Tomas
 * Date: 03.08.13
 * Time: 20:34
 */
class ComponentSuite extends WordSpec {

  override val nestedSuites = Vector(
    new ComponentSpec,
    new ComponentContainerSpec
  )

  "Components" should {

    "fit all specifications in package" in {}

  }
}
