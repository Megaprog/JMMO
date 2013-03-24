/*
 * Copyright (C) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.grid

import scala.concurrent.stm._
import scala.collection.mutable.Set

/**
 *
 * @author Tomas Shestakov
 * @tparam A type of object for adding to the Grid.
 */
trait MTStmArrayMutSetGridData[A] extends MTStmArrayGridData[A] {

  protected def makeSet: Set[A] = {
    //make Concurrent Set for multi-threaded access
    import collection.JavaConversions._
    new java.util.concurrent.CopyOnWriteArraySet[A]()
  }

  override def addObject(obj: A) {
    atomic {
      implicit txn =>
        for (cell <- objectCells(obj); if isCellInGrid(cell)) {
          (stmArray(cell.cx)(cell.cy) match {
            case null =>
              val set = makeSet
              stmArray(cell.cx)(cell.cy) = set
              set
            case set => set match {
              case objects: Set[A] => objects
            }
          }) += obj
        }
    }
  }

  override def removeObject(obj: A) {
    atomic {
      implicit txn =>
        for (cell <- objectCells(obj); if isCellInGrid(cell)) {
          stmArray(cell.cx)(cell.cy) match {
            case null => //may be needed throw Exception
            case set => set match {
              case objects: Set[A] =>
                objects -= obj
                if (objects.isEmpty) {
                  stmArray(cell.cx)(cell.cy) = null
                }
            }
          }
        }
    }
  }

  //  protected def addObjectToCell(obj: A, cell: Cell) {
  //    atomic { implicit txn =>
  //      stmMap.get(cell) match {
  //        case Some(update) => update match {
  //          case objects: Set[A] => objects
  //        }
  //        case None =>
  //          val update = makeSet
  //          stmMap(cell) = update
  //          update
  //      }
  //    } += obj
  //  }
  //
  //  protected def removeObjectFromCell(obj: A, cell: Cell) {
  //    atomic { implicit txn =>
  //      stmMap.get(cell) match {
  //        case Some(update) => update match {
  //          case objects: Set[A] =>
  //            objects -= obj
  //            if (objects.isEmpty) {
  //              stmMap -= cell
  //            }
  //        }
  //        case None =>  //may be needed throw Exception
  //      }
  //    }
  //  }

}
