/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.WordSpec
import jmmo.util.Cell

/**
 * It is required a lot of memory. Use -Xmx2048m option to start.
 *
 * This Specification don't correspond to any Class or Trait.
 * It runs [[jmmo.grid.MTBlockingGridDataSpec]] many times
 * to check multi-threading execution.
 *
 * Can be run with -c option to run tests in parallel
 * in which case last test must be ignored
 * @author Tomas Shestakov
 */
class MTSeriesSpec(grid: AbstractGrid[Dummy], numTests: Int = 1, numParallel: Int = 4, numObjects: Int = 400000)
  extends WordSpec with ShouldMatchers {

  def this() = this(new TestGrid(1000, 6000, 6000) with STArrayGridData[Dummy] /*MTConcMapGridData[Dummy]*/ /*MTStmArrayImmSetGridData[Dummy]*/
                                                   with STImmSetGridData[Dummy] /*MTConcMutSetGridData[Dummy]*/
                                                   with /*MTBlockingGridData[Dummy]*/ /*MTNonBlockingStmGridData[Dummy]*/
                                                        MTPartialLocksGridData[Dummy] {
    def numLockedCells = 10
  })

  override val nestedSuites = {
    Vector.fill(numTests)(new MTBlockingGridDataSpec(grid, numParallel, numObjects, false))
  }

  //  override val nestedSuites = {
  //    List.fill(numTests)(new MTBlockingGridDataSpec(new AbstractGrid[Dummy]
  //                            with STArrayGridData[Dummy] /*MTConcMapGridData[Dummy]*/ with MTBlockingGridData[Dummy] {
  //                              def cellSize = 10
  //                              def width = 300
  //                              def height = 300
  //                        }, numParallel, numObjects))
  //  }

  "An MTSeriesSpec" should {

    "fit MTBlockingGridDataSpec " + numTests + " times" in {}

    "after execution all MTBlockingGridDataSpecs grid must be empty" in {

      {
        var res = true
        (0 until grid.width).foreach {
          x =>
            (0 until grid.height).foreach {
              y =>
                val cell = Cell(x, y)
                if (!grid.getObjectsInCell(cell).isEmpty) {
                  println(cell + " is not empty")
                  res = false
                }
            }
        }
        res
      } should be(true)

    }

  }

}
