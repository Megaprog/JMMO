/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * @author Tomas Shestakov
 */
class STMapGridDataSpec extends WordSpec with ShouldMatchers {

  override val nestedSuites = {
    List(new AbstractGridSpec(new TestGrid(10, 200, 200) with STMapGridData[Dummy] with STMutSetGridData[Dummy]))
  }

  "An STMapGridData" should {

    "fit AbstractGridSpec with AbstractGrid mixing with STMapGridData" in {}

  }

}
