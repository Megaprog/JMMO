/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * @author Tomas Shestakov
 */
class MTStmMapMutSetGridDataSpec extends WordSpec with ShouldMatchers {

  override val nestedSuites = {
    Vector(new MTBlockingGridDataSpec(new TestGrid(10, 200, 200) with MTStmMapMutSetGridData[Dummy]))
  }

  "An MTStmMapMutSetGridData" should {

    "fit MTBlockingGridDataSpec with AbstractGrid mixing with MTStmMapMutSetGridData" in {}

  }

}
