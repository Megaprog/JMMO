/*
 * Copyright (C) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.grid

import scala.concurrent.stm._
import scala.collection.immutable.Set

/**
 *
 * @author Tomas Shestakov
 * @tparam A type of object for adding to the Grid.
 */
trait MTStmMapImmSetGridData[A] extends MTStmMapGridData[A] {

  protected def makeSet: Set[A] = {
    Set.empty[A]
  }

  override def addObject(obj: A) {
    atomic {
      implicit txn =>
        for (cell <- objectCells(obj); if isCellInGrid(cell)) {
          stmMap(cell) = (stmMap.get(cell) match {
            case Some(set) =>
              set
            case None =>
              makeSet
          }) + obj
        }
    }
  }

  override def removeObject(obj: A) {
    atomic {
      implicit txn =>
        for (cell <- objectCells(obj); if isCellInGrid(cell)) {
          (stmMap.get(cell) match {
            case Some(set) =>
              val newSet = set - obj
              if (newSet.isEmpty)
                null
              else
                newSet
            case None => null //may be needed throw Exception
          }) match {
            case null => stmMap -= cell
            case set => stmMap(cell) = set
          }
        }
    }
  }

  //  protected def addObjectToCell(obj: A, cell: Cell) {
  //    atomic { implicit txn =>
  //      stmMap(cell) = (stmMap.get(cell) match {
  //                          case Some(update) =>
  //                            update
  //                          case None =>
  //                            makeSet
  //                     }) + obj
  //    }
  //  }
  //
  //  protected def removeObjectFromCell(obj: A, cell: Cell) {
  //    atomic { implicit txn =>
  //      (stmMap.get(cell) match {
  //        case Some(update) =>
  //          val newSet = update - obj
  //          if (newSet.isEmpty)
  //            null
  //          else
  //            newSet
  //        case None => null //may be needed throw Exception
  //      }) match {
  //        case null => stmMap -= cell
  //        case update => stmMap(cell) = update
  //      }
  //    }
  //  }

}
