/*
 * Copyright (C) 2013 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.util

import org.scalatest.{Tag, WordSpec}

/**
 * User: Tomas
 * Date: 03.08.13
 * Time: 20:34
 */
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
