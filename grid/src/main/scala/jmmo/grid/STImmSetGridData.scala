/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import scala.collection.immutable.Set
import jmmo.util.Cell

/**
 * Single threaded abstract Grid data model.
 * @tparam A type of object for adding to the Grid.
 */
trait STImmSetGridData[A] extends STGridData[A] {

  protected def makeSet: Set[A] = {
    Set.empty[A]
  }

  //  def addObject(obj: A)(implicit objToCells: (A) => Traversable[Cell]) {
  //    for (cell <- obj; if isCellInGrid(cell)) {
  //      innerData(cell) = (innerData(cell) match {
  //                          case null =>
  //                            makeSet
  //                          case update =>
  //                            update
  //                        }) + obj
  //    }
  //  }
  //
  //  def removeObject(obj: A)(implicit objToCells: (A) => Traversable[Cell]) {
  //    for (cell <- obj; if isCellInGrid(cell)) {
  //      innerData(cell) = (innerData(cell) match {
  //                          case null => null //may be needed throw Exception
  //                          case update =>
  //                            val objects = update - obj
  //                            if (objects.isEmpty)
  //                              null
  //                            else
  //                              objects
  //                        })
  //    }
  //  }

  protected def addObjectToCell(obj: A, cell: Cell) {
    innerData(cell) = (innerData(cell) match {
      case null =>
        makeSet
      case set =>
        set
    }) + obj
  }

  protected def removeObjectFromCell(obj: A, cell: Cell) {
    innerData(cell) = (innerData(cell) match {
      case null => null //may be needed throw Exception
      case set =>
        val objects = set - obj
        if (objects.isEmpty)
          null
        else
          objects
    })
  }

}
