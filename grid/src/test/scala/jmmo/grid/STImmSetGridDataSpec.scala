/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * @author Tomas Shestakov
 */
class STImmSetGridDataSpec extends WordSpec with ShouldMatchers {

  override val nestedSuites = {
    List(new AbstractGridSpec(new TestGrid(10, 200, 200) with STArrayGridData[Dummy] with STImmSetGridData[Dummy]))
  }

  "An STImmSetGridData" should {

    "fit AbstractGridSpec with AbstractGrid mixing with STImmSetGridData" in {}

  }

}
