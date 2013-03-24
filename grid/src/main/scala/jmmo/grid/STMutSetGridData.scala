/*
 * Copyright (C) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.grid

import scala.collection.mutable.Set
import jmmo.util.Cell

/**
 * Single threaded abstract Grid data model.
 * @tparam A type of object for adding to the Grid.
 */
trait STMutSetGridData[A] extends STGridData[A] {

  protected def makeSet: Set[A] = {
    Set.empty[A]
  }

  //  def addObject(obj: A)(implicit objToCells: (A) => Traversable[Cell]) {
  //    for (cell <- obj; if isCellInGrid(cell)) {
  //      (innerData(cell) match {
  //        case null =>
  //          val update = makeSet
  //          innerData(cell) = update
  //          update
  //        case update: Set[A] =>
  //          update
  //      }) += obj
  //    }
  //  }
  //
  //  def removeObject(obj: A)(implicit objToCells: (A) => Traversable[Cell]) {
  //    for (cell <- obj; if isCellInGrid(cell)) {
  //      innerData(cell) match {
  //        case null =>  //may be needed throw Exception
  //        case update: Set[A] =>
  //          update -= obj
  //          if (update.isEmpty) {
  //            innerData(cell) = null
  //          }
  //      }
  //    }
  //  }

  protected def addObjectToCell(obj: A, cell: Cell) {
    //    val tmp = innerArray(cell.cx)(cell.cy)
    //    val update = if (tmp != null) tmp else { val ns = makeSet; innerArray(cell.cx)(cell.cy) = ns; ns }
    //    update += obj
    (innerData(cell) match {
      case null =>
        val set = makeSet
        innerData(cell) = set
        set
      case set: Set[A] =>
        set
    }) += obj
  }

  protected def removeObjectFromCell(obj: A, cell: Cell) {
    innerData(cell) match {
      case null => //may be needed throw Exception
      case set: Set[A] =>
        set -= obj
        if (set.isEmpty) {
          innerData(cell) = null
        }
    }
  }

}
