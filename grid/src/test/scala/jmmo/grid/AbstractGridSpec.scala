/*
 * Copyright (C) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.grid

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.WordSpec
import jmmo.util.Cell


/**
 * @author Tomas Shestakov
 */
class AbstractGridSpec(gridObjects: AbstractGrid[Dummy]) extends WordSpec with ShouldMatchers {

  def this() = this(new TestGrid(1000, 200, 200) with STMapGridData[Dummy] with STMutSetGridData[Dummy])

  override val nestedSuites = {
    List(new GridDimensionSpec(gridObjects), new GridObjectsSpec(gridObjects), new GridSelectionSpec(gridObjects))
  }

  //  override protected def withFixture(test: NoArgTest) {
  //    test.configMap.get("GridClass") match {
  //      case Some(s) => println(s)
  //      case None => println("None")
  //    }
  //    test()
  //  }

  "An AbstractGrid" should {

    "fit GridDimensionSpec" in {}
    "fit GridObjectsSpec" in {}
    "fit GridSelectionSpec" in {}

    "provide getObjectsInCell which returns empty Set if Cell is out of a Grid" in {
      gridObjects.getObjectsInCell(Cell(-1, -1)) should be(Set.empty)
    }
  }

}
