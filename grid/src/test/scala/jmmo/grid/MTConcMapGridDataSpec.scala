/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * @author Tomas Shestakov
 */
class MTConcMapGridDataSpec extends WordSpec with ShouldMatchers {

  override val nestedSuites = {
    List(new MTBlockingGridDataSpec(new TestGrid(10, 200, 200) with MTConcMapGridData[Dummy]
      with STImmSetGridData[Dummy] with MTBlockingGridData[Dummy]))
  }

  "An MTConcMapGridData" should {

    "fit MTBlockingGridDataSpec with AbstractGrid mixing with MTConcMapGridData" in {}

  }

}
