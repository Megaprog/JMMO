/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import jmmo.util.{Point2d, Cell}

/**
 * @author Tomas Shestakov
 */
class GridDimensionSpec(gridDim: GridDimension) extends WordSpec with ShouldMatchers {

  def this() = this(new GridDimension {
    val cellSize = 10
    val width = 200
    val height = 100
  })

  "A GridDimension" should {

    import gridDim._

    "has cellSize Greater than 0" in {
      cellSize should be > (0)
    }

    "has width Greater than 0" in {
      width should be > (0)
    }

    "has height Greater than 0" in {
      height should be > (0)
    }

    "provide halfCell, which returns half of Cell size (cellSize/2)" in {
      halfCell should be(cellSize / 2)
    }

    "provide outerWidth, which returns grid width in outer (points) dimension" in {
      outerWidth should be(width * cellSize)
    }

    "provide outerHeight, which returns grid height in outer (points) dimension" in {
      outerHeight should be(height * cellSize)
    }

    "provide outerToInner, which converts outer (point) coordinate to inner (cell) coordinate" in {
      val outer = 120
      outerToInner(outer) should be(outer / cellSize)
    }

    "provide innerToOuter, which converts inner (cell) coordinate to outer (point) coordinate" in {
      val inner = 25
      innerToOuter(inner) should be(inner * cellSize)
    }

    "provide innerToOuterInCenter, which converts inner (cell) coordinate to outer (point) center coordinate" in {
      val inner = 25
      innerToOuterInCenter(inner) should be(inner * cellSize + cellSize / 2)
    }

    "provide pointToCell, which converts outer Point2d to inner Cell location in the Grid" in {
      val x = 38
      val y = 56
      pointToCell(Point2d(x, y)) should be(Cell(x / cellSize, y / cellSize))
    }

    "provide cellToPoint, which converts inner Cell to outer Point2d location in the Grid" in {
      val x = 7
      val y = 4
      info("result Point2d will has the least coordinates in a Cell")
      cellToPoint(Cell(x, y)) should be(Point2d(x * cellSize, y * cellSize))
    }

    "provide cellToPointInCenter, which converts inner Cell to outer Point2d location in the Grid" in {
      val x = 7
      val y = 4
      info("result Point2d will has coordinates of the center in a Cell")
      cellToPointInCenter(Cell(x, y)) should be(Point2d(x * cellSize + cellSize / 2, y * cellSize + cellSize / 2))
    }

    "provide isPointInGrid, which checks if a Point is inside the Grid" in {
      info("if Point2d has zero coordinates")
      isPointInGrid(Point2d(0, 0)) should be(true)
      info("or Point2d is inside the Grid")
      isPointInGrid(Point2d(outerWidth / 2, outerHeight / 2)) should be(true)
      info("or Point2d has the largest outer coordinates of the Grid")
      isPointInGrid(Point2d(outerWidth - 1, outerHeight - 1)) should be(true)
      info("isPointInGrid returns TRUE")

      info("if one or two of a Point2d coordinates is less or equal -cellSize")
      isPointInGrid(Point2d(-cellSize, 0)) should be(false)
      isPointInGrid(Point2d(0, -cellSize)) should be(false)
      info("or X coordinate of a Point2d is more or equal outerWidth")
      isPointInGrid(Point2d(outerWidth, outerHeight - 1)) should be(false)
      info("or Y coordinate of a Point2d is more or equal outerHeight")
      isPointInGrid(Point2d(outerWidth - 1, outerHeight)) should be(false)
      info("or Point2d has negative coordiantes")
      isPointInGrid(Point2d(-1, -1)) should be(false)
      info("isPointInGrid returns FALSE")
    }

    "provide isCellInGrid, which checks if a Cell is inside the Grid" in {
      info("if Cell has zero coordinates")
      isCellInGrid(Cell(0, 0)) should be(true)
      info("or Cell is inside the Grid")
      isCellInGrid(Cell(width / 2, height / 2)) should be(true)
      info("or Cell has the largest inner coordinates of the Grid")
      isCellInGrid(Cell(width - 1, height - 1)) should be(true)
      info("or Cell has the least inner coordinates of the Grid")
      isCellInGrid(Cell(0, 0)) should be(true)
      info("isPointInGrid returns TRUE")

      info("if one or two of a Cell coordinates is less than 0")
      isCellInGrid(Cell(-1, 0)) should be(false)
      isCellInGrid(Cell(0, -1)) should be(false)
      info("or X coordinate of a Cell is more or equal outerWidth")
      isCellInGrid(Cell(width, height - 1)) should be(false)
      info("or Y coordinate of a Cell is more or equal outerHeight")
      isCellInGrid(Cell(width - 1, height)) should be(false)
      info("isPointInGrid returns FALSE")
    }

    "provide inSameCell, which checks if two points is inside same cell" in {
      import util.Random
      val cell = Cell(Random.nextInt(width), Random.nextInt(height))
      inSameCell(cellToPoint(cell), cellToPointInCenter(cell)) should be(true)
      inSameCell(cellToPoint(cell), cellToPoint(cell).plus(cellSize, 0)) should be(false)
    }
  }
}
