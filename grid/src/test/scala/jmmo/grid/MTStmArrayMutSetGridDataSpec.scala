/*
 * Copyright (C) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.grid

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * @author Tomas Shestakov
 */
class MTStmArrayMutSetGridDataSpec extends WordSpec with ShouldMatchers {

  override val nestedSuites = {
    List(new MTBlockingGridDataSpec(new TestGrid(10, 200, 200) with MTStmArrayMutSetGridData[Dummy]))
  }

  "An MTStmArrayMutSetGridData" should {

    "fit MTBlockingGridDataSpec with AbstractGrid mixing with MTStmArrayMutSetGridData" in {}

  }

}
