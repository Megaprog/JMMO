/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import jmmo.util.Cell

/**
 * Force an [[jmmo.grid.AbstractGrid]] to
 * throw IllegalArgumentException if Cell is out of a Grid
 * in `getObjectsInCell` method
 * @author Tomas Shestakov
 * @tparam A type of object for adding to the Grid.
 */
trait ErrorIfOutOfGrid[A] extends AbstractGrid[A] {

  abstract override def getObjectsInCell(cell: Cell, filter: A => Boolean = null) = {
    if (!isCellInGrid(cell))
      throw new IllegalArgumentException("Coordinates of the " + cell + " is out of Grid")
    else
      null
  }

}
