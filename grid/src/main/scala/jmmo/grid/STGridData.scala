/*
 * Copyright (C) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.grid

import scala.collection.Set
import jmmo.util.Cell

/**
 * Single threaded abstract Grid data model.
 * @tparam A type of object for adding to the Grid.
 */
trait STGridData[A] extends AbstractGrid[A] {

  protected def makeSet: Set[A]

  /**
   * Add an object to a Cell
   * @param obj an object to add
   * @param cell a cell to where object will be added
   */
  protected def addObjectToCell(obj: A, cell: Cell)

  /**
   * Remove an object from a Cell
   * @param obj an object to remove
   * @param cell a cell from where object will be removed
   */
  protected def removeObjectFromCell(obj: A, cell: Cell)

  override def addObject(obj: A) {
    for (cell <- objectCells(obj); if isCellInGrid(cell)) {
      addObjectToCell(obj, cell)
    }
  }

  override def removeObject(obj: A) {
    for (cell <- objectCells(obj); if isCellInGrid(cell)) {
      removeObjectFromCell(obj, cell)
    }
  }

  /**
   * Inner data structure
   * @tparam A type of object for adding to the data structure
   */
  abstract class InnerData[A] {

    /**
     * Get objects in a Cell from data structure
     * @param cell a Cell we want to get objects from
     * @return Set of objects or null if no one objects are in the Cell
     */
    def apply(cell: Cell): Set[A]

    /**
     * Update a Cell in the data structure.
     * @param cell a Cell we want to update
     * @param set Set of objects must be in the Cell or null if we want to clear the Cell
     */
    def update(cell: Cell, set: Set[A])
  }

  protected def innerData: InnerData[A]

  abstract override def getObjectsInCell(cell: Cell, filter: A => Boolean = null): scala.collection.Set[A] = {
    super.getObjectsInCell(cell, filter) match {
      case null => {
        innerData(cell) match {
          case null => scala.collection.immutable.Set.empty[A]
          case objects => if (filter == null) objects else objects.filter(filter)
        }
      }
      case objects => objects
    }
  }

}
