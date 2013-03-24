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
trait MTStmArrayImmSetGridData[A] extends MTStmArrayGridData[A] {

  protected def makeSet: Set[A] = {
    Set.empty[A]
  }

  override def addObject(obj: A) {
    atomic { implicit txn =>
      for (cell <- objectCells(obj); if isCellInGrid(cell)) {
        stmArray(cell.cx)(cell.cy) = (stmArray(cell.cx)(cell.cy) match {
          case null =>
            makeSet
          case set =>
            set
        }) + obj
      }
    }
  }

  override def removeObject(obj: A) {
    atomic {
      implicit txn =>
        for (cell <- objectCells(obj); if isCellInGrid(cell)) {
          stmArray(cell.cx)(cell.cy) = (stmArray(cell.cx)(cell.cy) match {
            case null => null //may be needed throw Exception
            case set =>
              val newSet = set - obj
              if (newSet.isEmpty)
                null
              else
                newSet
          })
        }
    }
  }

}
