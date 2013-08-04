/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import jmmo.util.Cell


/**
 * @author Tomas Shestakov
 */
class ErrorIfOutOfGridSpec extends WordSpec with ShouldMatchers {

  "An ErrorIfOutOfGrid" should {

    "override getObjectsInCell to throw IllegalArgumentException if Cell is out of a Grid" in {
      val grid = new TestGrid(10, 100, 100) with ErrorIfOutOfGrid[Dummy]
        with STMapGridData[Dummy] with STMutSetGridData[Dummy]

      val caught = evaluating {
        grid.getObjectsInCell(Cell(-1, -1)) should be(Set.empty)
      } should produce[IllegalArgumentException]

      info(caught.getMessage)
    }
  }

}
