/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * @author Tomas Shestakov
 */
class STMutSetGridDataSpec extends WordSpec with ShouldMatchers {

  override val nestedSuites = {
    Vector(new AbstractGridSpec(new TestGrid(10, 200, 200) with STArrayGridData[Dummy] with STMutSetGridData[Dummy]))
  }

  "An STMutSetGridData" should {

    "fit AbstractGridSpec with AbstractGrid mixing with STMutSetGridData" in {}

  }

}
