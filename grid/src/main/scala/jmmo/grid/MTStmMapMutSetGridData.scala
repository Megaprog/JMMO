/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import scala.concurrent.stm._
import scala.collection.mutable.Set

/**
 *
 * @author Tomas Shestakov
 * @tparam A type of object for adding to the Grid.
 */
trait MTStmMapMutSetGridData[A] extends MTStmMapGridData[A] {

  protected def makeSet: Set[A] = {
    //make Concurrent Set for multi-threaded access
    import collection.JavaConversions._
    new java.util.concurrent.CopyOnWriteArraySet[A]()
  }

  override def addObject(obj: A) {
    atomic {
      implicit txn =>
        for (cell <- objectCells(obj); if isCellInGrid(cell)) {
          (stmMap.get(cell) match {
            case Some(set) => set match {
              case objects: Set[A] => objects
            }
            case None =>
              val set = makeSet
              stmMap(cell) = set
              set
          }) += obj
        }
    }
  }

  override def removeObject(obj: A) {
    atomic {
      implicit txn =>
        for (cell <- objectCells(obj); if isCellInGrid(cell)) {
          stmMap.get(cell) match {
            case Some(set) => set match {
              case objects: Set[A] =>
                objects -= obj
                if (objects.isEmpty) {
                  stmMap -= cell
                }
            }
            case None => //may be needed throw Exception
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
