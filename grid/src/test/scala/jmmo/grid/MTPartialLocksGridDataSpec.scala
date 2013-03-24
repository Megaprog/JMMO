/*
 * Copyright (C) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.grid

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * @author Tomas Shestakov
 */
class MTPartialLocksGridDataSpec extends WordSpec with ShouldMatchers {

  override val nestedSuites = {
    List(new MTBlockingGridDataSpec(new TestGrid(10, 200, 200) with /*MTConcMapGridData[Dummy]*/ STArrayGridData[Dummy]
                                     with STImmSetGridData[Dummy] with MTPartialLocksGridData[Dummy] {
      def numLockedCells = 10
    }))
  }

  "An MTPartialLocksGridData" should {

    "fit MTBlockingGridDataSpec with AbstractGrid mixing with MTPartialLocksGridData" in {}

  }

}