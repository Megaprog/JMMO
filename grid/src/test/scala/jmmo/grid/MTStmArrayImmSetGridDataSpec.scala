/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * @author Tomas Shestakov
 */
class MTStmArrayImmSetGridDataSpec extends WordSpec with ShouldMatchers {

  override val nestedSuites = {
    Vector(new MTBlockingGridDataSpec(new TestGrid(10, 200, 200) with MTStmArrayImmSetGridData[Dummy]))
  }

  "An MTStmArrayImmSetGridData" should {

    "fit MTBlockingGridDataSpec with AbstractGrid mixing with MTStmArrayImmSetGridData" in {}

  }

}
