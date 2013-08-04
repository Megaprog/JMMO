/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.WordSpec
import util.Random
import scala.collection.Set
import java.awt.Rectangle
import jmmo.util.{Cell, Point2d}

class Dummy(private var pos: Point2d, private val cells: Set[Cell]) {

  def move(toPos: Point2d) {
    this.pos = toPos
  }

  def getPos() = this.pos

  def getCells(dim: GridDimension): Traversable[Cell] = {
    val cellPos = dim.pointToCell(this.pos)
    cells map (cell => cellPos plus cell)
  }

  override def toString = "Dummy[" + pos + ", " + cells.toString() + "]"
}

/**
 * @author Tomas Shestakov
 */
class GridObjectsSpec(gridObjects: AbstractGrid[Dummy], numObjects: Int = 1000) extends WordSpec with ShouldMatchers {

  def this() = this(new TestGrid(10, 100, 100) /*with ErrorIfOutOfGrid[Dummy]*/
    with STArrayGridData[Dummy] /*STMapGridData[Dummy]*/ with STMutSetGridData[Dummy])

  import gridObjects._

  //  val numObjects = 1000
  val maxCells = 8
  val maxRadius = 2

  val objects = {

    val objects = scala.collection.mutable.Set.empty[Dummy]
    //generate test Dummy objects
    for (i <- 1 to numObjects) {
      val pos = Point2d(Random.nextInt(outerWidth), Random.nextInt(outerHeight))
      def makeCells(count: Int): Set[Cell] = {
        if (count == 0)
        //Use Set because cells can be repeated
          Set.empty[Cell]
        else
          makeCells(count - 1) + Cell(Random.nextInt(maxRadius + 1), Random.nextInt(maxRadius + 1))
      }
      val cells = makeCells(Random.nextInt(maxCells + 1))

      val dummy = new Dummy(pos, cells)
      objects += dummy

      //      println("%07d ".format(i) + dummy)
    }
    objects
  }

  def checkObjectsInGrid(grid: AbstractGrid[Dummy], objs: Set[Dummy]): Boolean = {
    var res = true

    //check if all objects are in grid
    objs.foreach {
      obj =>
        obj.getCells(grid).foreach {
          cell =>
            if (grid.isCellInGrid(cell)) {
              if (!grid.getObjectsInCell(cell).contains(obj)) {
                println(cell + " of " + obj + " not found in Grid")
                res = false
              }
            }
        }
    }

    //check if grid not contains unnecessary objects
    (0 until grid.width).foreach {
      x =>
        (0 until grid.height).foreach {
          y =>
            val cell = Cell(x, y)
            grid.getObjectsInCell(cell).foreach {
              obj =>
                if (!objs(obj)) {
                  println(obj + " in " + cell + " not found in Objects")
                  res = false
                }
                if (!obj.getCells(grid).exists(_ == cell)) {
                  println(obj + " in " + cell + " don't occupy this cell")
                  res = false
                }
            }
        }
    }

    res
  }

  "A GridObjects" should {

    "provide addObject, which add an Object to the Grid" in {
      objects foreach addObject
      checkObjectsInGrid(gridObjects, objects) should be(true)
    }

    "provide updateObject, update an Object in the Grid" in {
      //move part of objects
      val buf = objects.toIndexedSeq
      for (_ <- 1 to numObjects / 4 + Random.nextInt(numObjects / 4)) {
        val idx = Random.nextInt(buf.length)
        val obj = buf(idx)
        val newPos = Point2d(Random.nextInt(outerWidth), Random.nextInt(outerHeight))
        updateObject(obj, _.move(newPos))
      }
      checkObjectsInGrid(gridObjects, objects) should be(true)
    }

    "provide getObjectsInCell, which get Objects which satisfy filter from Grid Cell" in {
      {
        var res = true
        for (_ <- 1 to numObjects / 4 + Random.nextInt(numObjects / 4)) {
          val cell = Cell(Random.nextInt(width), Random.nextInt(height))
          getObjectsInCell(cell).foreach {
            obj =>
              if (!objects(obj)) {
                println(obj + " in " + cell + " not found in Objects")
                res = false
              }
              if (!objectCells(obj).exists(_ == cell)) {
                println(obj + " in " + cell + " don't occupy this cell")
                res = false
              }
          }
        }
        res
      } should be(true)
    }

    "provide getObjectsInPoint, which get Objects which satisfy filter from Grid Cell contains specified Point2d" in {
      {
        var res = true
        for (_ <- 1 to numObjects / 4 + Random.nextInt(numObjects / 4)) {
          val point = Point2d(Random.nextInt(outerWidth), Random.nextInt(outerHeight))
          val cell = pointToCell(point)
          if (getObjectsInPoint(point) != getObjectsInCell(cell)) {
            println("Objects in " + point + " not equals objects in " + cell)
            res = false
          }
        }
        res
      } should be(true)
    }

    "provide getObjectsInRectCell, which get Objects which satisfy filter from Grid Cells bounded by a rectangle" in {
      {
        var res = true
        for (_ <- 1 to numObjects / 4 + Random.nextInt(numObjects / 4)) {
          var cell1: Cell = null
          var cell2: Cell = null
          do {
            cell1 = Cell(Random.nextInt(width / 10), Random.nextInt(height / 10))
            cell2 = Cell(Random.nextInt(width / 10), Random.nextInt(height / 10))
          } while (cell2.cx <= cell1.cx || cell2.cy <= cell1.cy)
          getObjectsInRectCell(cell1, cell2).foreach {
            obj =>
              if (!objects(obj)) {
                println(obj + " in Rectangle(" + cell1 + ", " + cell2 + ") not found in Objects")
                res = false
              }
              if (!objectCells(obj).exists(cell => cell1.cx <= cell.cx && cell.cx <= cell2.cx &&
                cell1.cy <= cell.cy && cell.cy <= cell2.cy)) {
                println(obj + " in Rectangle(" + cell1 + ", " + cell2 + ") don't occupy this Rect")
                res = false
              }
          }
        }
        res
      } should be(true)
    }

    "provide getObjectsInRect, which get Objects which satisfy filter from Grid Cells bounded by a rectangle in points" in {
      {
        var res = true
        for (_ <- 1 to numObjects / 4 + Random.nextInt(numObjects / 4)) {
          var point1: Point2d = null
          var point2: Point2d = null
          do {
            point1 = Point2d(Random.nextInt(outerWidth / 10), Random.nextInt(outerHeight / 10))
            point2 = Point2d(Random.nextInt(outerWidth / 10), Random.nextInt(outerHeight / 10))
          } while (point2.x <= point1.x || point2.y <= point1.y)
          getObjectsInRect(point1, point2).foreach {
            obj =>
              if (!objects(obj)) {
                println(obj + " in Rectangle(" + point1 + ", " + point2 + ") not found in Objects")
                res = false
              }
              if (!objectCells(obj).exists(cell => {
                val p = cellToPointInCenter(cell)
                point1.x <= p.x && p.x <= point2.x && point1.y <= p.y && p.y <= point2.y
              })) {
                println(obj + " in Rectangle(" + point1 + ", " + point2 + ") don't occupy this Rect")
                res = false
              }
          }
        }
        res
      } should be(true);

      //getObjectsInRectCell(cell1, cell2) should be equal
      //getObjectsInRect(cellToPointInCenter(cell1), cellToPointInCenter(cell2))
      {
        var res = true
        for (_ <- 1 to numObjects / 8 + Random.nextInt(numObjects / 8)) {
          var cell1: Cell = null
          var cell2: Cell = null
          do {
            cell1 = Cell(Random.nextInt(width / 10), Random.nextInt(height / 10))
            cell2 = Cell(Random.nextInt(width / 10), Random.nextInt(height / 10))
          } while (cell2.cx <= cell1.cx || cell2.cy <= cell1.cy)
          if (getObjectsInRectCell(cell1, cell2) !=
            getObjectsInRect(cellToPointInCenter(cell1), cellToPointInCenter(cell2))) {
            println("Objects in Rect(" + cell1 + ", " + cell2 + ") are not equals Objects in RectCell")
            println("In RectCell:")
            getObjectsInRectCell(cell1, cell2) foreach println
            println("In Rect:")
            getObjectsInRect(cellToPointInCenter(cell1), cellToPointInCenter(cell2)) foreach println
            res = false
          }
        }
        res
      } should be(true)
    }

    "provide getObjectsInRadiusCell, which get Objects which satisfy filter from Grid Cells " +
      "bounded by by a circle with given radius" in {
      {
        var res = true
        for (_ <- 1 to numObjects / 8 + Random.nextInt(numObjects / 8)) {
          var center: Cell = null
          var radius = 0
          //          do {
          center = Cell(Random.nextInt(width), Random.nextInt(height))
          radius = Random.nextInt((width + height) / 20)
          //          } while ( !(isCellInGrid(center.plus(radius, 0)) && isCellInGrid(center.plus(-radius, 0)) &&
          //                      isCellInGrid(center.plus(0, radius)) && isCellInGrid(center.plus(0, -radius))) )
          getObjectsInRadiusCell(center, radius).foreach {
            obj =>
              if (!objects(obj)) {
                println(obj + " in Circle(" + center + ", " + radius + ") not found in Objects")
                res = false
              }
              if (!objectCells(obj).exists(cell => center.distance(cell) <= radius)) {
                println(obj + " in Circle(" + center + ", " + radius + ") don't occupy this Cyrcle")
                res = false
              }
          }
        }
        res
      } should be(true)
    }

    "provide getObjectsInRadius, which get Objects which satisfy filter from Grid Cells " +
      "bounded by by a circle with given radius in points" in {
      {
        var res = true
        for (_ <- 1 to numObjects / 8 + Random.nextInt(numObjects / 8)) {
          var center: Point2d = null
          var radius = 0
          //          do {
          center = Point2d(Random.nextInt(outerWidth), Random.nextInt(outerHeight))
          radius = Random.nextInt((outerWidth + outerHeight) / 20)
          //          } while ( !(isCellInGrid(center.plus(radius, 0)) && isCellInGrid(center.plus(-radius, 0)) &&
          //                      isCellInGrid(center.plus(0, radius)) && isCellInGrid(center.plus(0, -radius))) )
          getObjectsInRadius(center, radius).foreach {
            obj =>
              if (!objects(obj)) {
                println(obj + " in Circle(" + center + ", " + radius + ") not found in Objects")
                res = false
              }
              if (!objectCells(obj).exists(cell => center.distance(cellToPointInCenter(cell)) <= radius)) {
                println(obj + " in Circle(" + center + ", " + radius + ") don't occupy this Cyrcle")
                res = false
              }
          }
        }
        res
      } should be(true);

      //getObjectsInRadiusCell(center, radius) should be equal
      //getObjectsInRadius(cellToPointInCenter(center), innerToOuter(radius))
      {
        var res = true
        for (_ <- 1 to numObjects / 16 + Random.nextInt(numObjects / 16)) {
          var center: Cell = null
          var radius = 0
          //          do {
          center = Cell(Random.nextInt(width), Random.nextInt(height))
          radius = Random.nextInt((width + height) / 20)
          //          } while ( !(isCellInGrid(center.plus(radius, 0)) && isCellInGrid(center.plus(-radius, 0)) &&
          //                      isCellInGrid(center.plus(0, radius)) && isCellInGrid(center.plus(0, -radius))) )
          if (getObjectsInRadiusCell(center, radius) !=
            getObjectsInRadius(cellToPointInCenter(center), innerToOuter(radius))) {
            println("Objects in Radius(" + center + ", " + radius + ") are not equals Objects in RadiusCell")
            println("In RadiusCell:")
            getObjectsInRadiusCell(center, radius) foreach println
            println("In Radius:")
            getObjectsInRadius(cellToPointInCenter(center), innerToOuter(radius)) foreach println
            res = false
          }
        }
        res
      } should be(true)
    }

    "provide getObjectsInShape, which get Objects which satisfy filter from Grid Cells " +
      "bounded by by a java.awt.Shape" in {

      //Compare results getObjectsInRectCell and getObjectsInShape in same Rectangle
      {
        var res = true
        for (_ <- 1 to numObjects / 16 + Random.nextInt(numObjects / 16)) {
          var point1: Point2d = null
          var point2: Point2d = null
          do {
            point1 = Point2d(Random.nextInt(outerWidth / 10), Random.nextInt(outerHeight / 10))
            point2 = Point2d(Random.nextInt(outerWidth / 10), Random.nextInt(outerHeight / 10))
          } while (point2.x <= point1.x || point2.y <= point1.y)
          if (getObjectsInRect(point1, point2) !=
            getObjectsInShape(new Rectangle(point1.x, point1.y, point2.x - point1.x + 1, point2.y - point1.y + 1))) {
            println("Objects in Rectangle(" + point1 + ", " + point2 + ") are not equals Objects in same Shape")
            println("In Rect:")
            getObjectsInRect(point1, point2) foreach println
            println("In Shape:")
            getObjectsInShape(new Rectangle(point1.x, point1.y, point2.x - point1.x + 1, point2.y - point1.y + 1)) foreach println
            res = false
          }
        }
        res
      } should be(true);
    }

    "provide removeObject, which Remove an Object from the Grid" in {
      //remove part of objects
      val buf = objects.toIndexedSeq
      for (_ <- 1 to numObjects / 4 + Random.nextInt(numObjects / 4)) {
        var idx = 0
        var obj: Dummy = null
        do {
          idx = Random.nextInt(buf.length)
          obj = buf(idx)
        } while (!objects.contains(obj))

        removeObject(obj)
        objects -= obj
      }
      checkObjectsInGrid(gridObjects, objects) should be(true)

      //remove all objects
      objects foreach removeObject
      objects.clear()
      checkObjectsInGrid(gridObjects, objects) should be(true)
    }

  }

}
