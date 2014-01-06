/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * @author Tomas Shestakov
 */
class MTStmArrayMutSetGridDataSpec extends WordSpec with ShouldMatchers {

  override val nestedSuites = {
    Vector(new MTBlockingGridDataSpec(new TestGrid(10, 200, 200) with MTStmArrayMutSetGridData[Dummy]))
  }

  "An MTStmArrayMutSetGridData" should {

    "fit MTBlockingGridDataSpec with AbstractGrid mixing with MTStmArrayMutSetGridData" in {}

  }

}
