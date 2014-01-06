/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * @author Tomas Shestakov
 */
class MTNonBlockingStmGridDataSpec extends WordSpec with ShouldMatchers {

  override val nestedSuites = {
    Vector(new MTBlockingGridDataSpec(new TestGrid(10, 200, 200) with MTConcMapGridData[Dummy]
      with STImmSetGridData[Dummy] with MTNonBlockingStmGridData[Dummy] {
      def numLockedCells = 10
    }))
  }

  "An MTNonBlockingStmGridData" should {

    "fit MTBlockingGridDataSpec with AbstractGrid mixing with MTNonBlockingStmGridData" in {}

  }

}
