/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import org.scalatest.WordSpec

/**
 * It is required a lot of memory. Use -Xmx1024m option to start.
 *
 * @author Tomas Shestakov
 */
class GridSuite extends WordSpec {

  override val nestedSuites = Vector(
    new GridDimensionSpec,
    new GridObjectsSpec,
    new AbstractGridSpec,
    new ErrorIfOutOfGridSpec,
    new STArrayGridDataSpec,
    new STMapGridDataSpec,
    new MTConcMapGridDataSpec,
    new STImmSetGridDataSpec,
    new STMutSetGridDataSpec,
    new MTConcMutSetGridDataSpec,
    new MTBlockingGridDataSpec,
    new MTNonBlockingStmGridDataSpec,
    new MTPartialLocksGridDataSpec,
    new MTStmMapImmSetGridDataSpec,
    new MTStmMapMutSetGridDataSpec,
    new MTStmArrayImmSetGridDataSpec,
    new MTStmArrayMutSetGridDataSpec,
    new SingleLevelExactCacheSpec,
    new SingleLevelPosCacheSpec,
    new MTSeriesSpec(new TestGrid(1000, 500, 500) with STArrayGridData[Dummy] with STImmSetGridData[Dummy]
                      with MTBlockingGridData[Dummy], 1, 4, 100000)
  )

  "Grid Framework" should {

    "fit all specifications in package" in {}

  }

}
