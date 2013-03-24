/*
 * Copyright (C) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.grid

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * @author Tomas Shestakov
 */
class STArrayGridDataSpec extends WordSpec with ShouldMatchers {

  override val nestedSuites = {
    List(new AbstractGridSpec(new TestGrid(10, 200, 200) with STArrayGridData[Dummy] with STMutSetGridData[Dummy]))
  }

  "An STArrayGridData" should {

    "fit AbstractGridSpec with AbstractGrid mixing with STArrayGridData" in {}

  }

}
