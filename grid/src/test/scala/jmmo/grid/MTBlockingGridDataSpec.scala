/*
 * Copyright (C) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.grid

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.WordSpec
import org.scalatest.concurrent.Conductors
import scala.collection.mutable.Set
import util.Random
import jmmo.util.{Point2d, Cell}
import org.scalatest.time.{Seconds, Millis, Span}

/**
 * @author Tomas Shestakov
 */
class MTBlockingGridDataSpec(grid: AbstractGrid[Dummy], numParallel: Int = 4, numObjects: Int = 20000, checkIntegrity: Boolean = true)
  extends WordSpec with ShouldMatchers with Conductors {

  def this() = this(new TestGrid(10, 200, 200) with STArrayGridData[Dummy] /*MTConcMapGridData[Dummy]*/
                                               with STImmSetGridData[Dummy] /*MTConcMutSetGridData[Dummy]*/
                                               with MTBlockingGridData[Dummy])

  override val nestedSuites = {
    //    List(new AbstractGridSpec(new TestGrid(10, 100, 100) with STArrayGridData[Dummy]
    //                                                         with STMutSetGridData[Dummy]
    //                                                         with MTBlockingGridData[Dummy] ))
    List(new AbstractGridSpec(grid))
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
    res
  }

  import collection.JavaConversions._

  val objects: Set[Dummy] = java.util.Collections.newSetFromMap(new java.util.concurrent.ConcurrentHashMap[Dummy, java.lang.Boolean]())

  val maxCells = 8
  val maxRadius = 2

  import grid._

  "An MTBlockingGridData" should {

    "fit AbstractGridSpec with AbstractGrid mixing with MTBlockingGridData" in {}

    "pass multi-threaded tests" in {

      val conductor = new Conductor
      import conductor._

      for (f <- 1 to numParallel) {
        thread("ObjectGenerator" + f) {

          //info("Thread=" + java.lang.Thread.currentThread())
          info("adding objects")
          val objs = scala.collection.mutable.Set.empty[Dummy]
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
            addObject(dummy)
            objs += dummy
            //println("%07d ".format(i) + dummy)
          }

          checkObjectsInGrid(grid, objs) should be(true)

          info("moving part of objects")
          val buf = objs.toIndexedSeq
          for (_ <- 1 to numObjects / 2 + Random.nextInt(numObjects / 2)) {
            val idx = Random.nextInt(buf.length)
            val obj = buf(idx)
            val newPos = Point2d(Random.nextInt(outerWidth), Random.nextInt(outerHeight))
            updateObject(obj, _.move(newPos))
          }

          checkObjectsInGrid(grid, objs) should be(true)

          info("removing part of objects")
          for (_ <- 1 to numObjects / 4 + Random.nextInt(numObjects / 4)) {
            var idx = 0
            var obj: Dummy = null
            do {
              idx = Random.nextInt(buf.length)
              obj = buf(idx)
            } while (!objs.contains(obj))
            removeObject(obj)
            objs -= obj
          }

          checkObjectsInGrid(grid, objs) should be(true)

          objects ++= objs
        }
      }

      //if use whenFinished timeout occur
      //conduct(10000, 600)
      conduct(Timeout(Span(600, Seconds)), Interval(Span(10000, Millis)))

      //      whenFinished {
      if (checkIntegrity) {
        info("check Grid integrity");
        //check if grid not contains unnecessary objects
        {
          var res = true
          (0 until grid.width).foreach {
            x =>
              (0 until grid.height).foreach {
                y =>
                  val cell = Cell(x, y)
                  grid.getObjectsInCell(cell).foreach {
                    obj =>
                      if (!objects(obj)) {
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
        } should be(true)
      }

      //remove all objects from grid
      objects foreach removeObject
      //      }

    }

  }

}
