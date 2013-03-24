/*
 * Copyright (C) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.grid

import scala.concurrent.stm._
import scala.collection.Set
import jmmo.util.Cell

/**
 * Multi-threaded not blocking Grid data trait based on ScalaSTM library TMap.
 * Child traits use immutable [[jmmo.grid.MTStmMapImmSetGridData]]
 * or mutable [[jmmo.grid.MTStmMapMutSetGridData]] Sets.
 * @author Tomas Shestakov
 * @tparam A type of object for adding to the Grid.
 */
trait MTStmMapGridData[A] extends AbstractGrid[A] {

  protected val stmMap: TMap[Cell, Set[A]] = TMap.empty

  abstract override def getObjectsInCell(cell: Cell, filter: A => Boolean = null): scala.collection.Set[A] = {
    super.getObjectsInCell(cell, filter) match {
      case null => {
        atomic(implicit txn => stmMap.get(cell)) match {
          case Some(objects) => if (filter == null) objects else objects.filter(filter)
          case None => scala.collection.immutable.Set.empty[A]
        }
      }
      case objects => objects
    }
  }

}
