/*
 * Copyright (C) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.grid

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * @author Tomas Shestakov
 */
class MTStmMapImmSetGridDataSpec extends WordSpec with ShouldMatchers {

  override val nestedSuites = {
    List(new MTBlockingGridDataSpec(new TestGrid(10, 200, 200) with MTStmMapImmSetGridData[Dummy]))
  }

  "An MTStmMapImmSetGridData" should {

    "fit MTBlockingGridDataSpec with AbstractGrid mixing with MTStmMapImmSetGridData" in {}

  }

}
