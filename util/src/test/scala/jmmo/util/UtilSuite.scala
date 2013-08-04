/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.util

import org.scalatest.WordSpec
import tags.Suite

/**
 * User: Tomas
 * Date: 03.08.13
 * Time: 20:34
 */
@Suite
class UtilSuite extends WordSpec {

  override val nestedSuites = List(
    new PointsSpec,
    new MapCountAllSpec,
    new MapSetAllSpec
  )

  "Util Framework" should {

    "fit all specifications in package" in {}

  }
}
