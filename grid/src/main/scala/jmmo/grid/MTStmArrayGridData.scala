/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import scala.concurrent.stm._
import scala.collection.Set
import jmmo.util.Cell

/**
 * Multi-threaded not blocking Grid data trait based on ScalaSTM library TArray.
 * Child traits use immutable [[jmmo.grid.MTStmMapImmSetGridData]]
 * or mutable [[jmmo.grid.MTStmMapMutSetGridData]] Sets.
 * @author Tomas Shestakov
 * @tparam A type of object for adding to the Grid.
 */
trait MTStmArrayGridData[A] extends AbstractGrid[A] {

  protected val stmArray = Array.fill(width)(TArray.ofDim[Set[A]](height))

  abstract override def getObjectsInCell(cell: Cell, filter: A => Boolean = null): scala.collection.Set[A] = {
    super.getObjectsInCell(cell, filter) match {
      case null => {
        atomic(implicit txn => stmArray(cell.cx)(cell.cy)) match {
          case null => scala.collection.immutable.Set.empty[A]
          case objects => if (filter == null) objects else objects.filter(filter)
        }
      }
      case objects => objects
    }
  }

}
