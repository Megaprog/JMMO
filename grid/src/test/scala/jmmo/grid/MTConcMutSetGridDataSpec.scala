/*
 * Copyright (C) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.grid

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * @author Tomas Shestakov
 */
class MTConcMutSetGridDataSpec extends WordSpec with ShouldMatchers {

  override val nestedSuites = {
    List(new MTBlockingGridDataSpec(new TestGrid(10, 200, 200) with STArrayGridData[Dummy]
      with MTConcMutSetGridData[Dummy] with MTBlockingGridData[Dummy]))
  }

  "An MTConcMutSetGridData" should {

    "fit MTBlockingGridDataSpec with AbstractGrid mixing with MTConcMutSetGridData" in {}

  }

}
