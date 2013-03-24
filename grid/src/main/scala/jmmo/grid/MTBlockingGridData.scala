/*
 * Copyright (C) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.grid

import jmmo.util.Cell

/**
 * Multi-threaded blocking Grid data trait.
 * On each add or remove object operation Grid will be blocking.
 * Read operations are not blocking.
 * @author Tomas Shestakov
 * @tparam A type of object for adding to the Grid.
 */
trait MTBlockingGridData[A] extends STGridData[A] {

  //  abstract override def addObject(obj: A)(implicit objToCells: (A) => Traversable[Cell]) {
  //    innerData.synchronized {
  //      super.addObject(obj)
  //    }
  //  }
  //
  //  abstract override def removeObject(obj: A)(implicit objToCells: (A) => Traversable[Cell]) {
  //    innerData.synchronized {
  //      super.removeObject(obj)
  //    }
  //  }

  protected abstract override def addObjectToCell(obj: A, cell: Cell) {
    innerData.synchronized {
      super.addObjectToCell(obj, cell)
    }
  }

  protected abstract override def removeObjectFromCell(obj: A, cell: Cell) {
    innerData.synchronized {
      super.removeObjectFromCell(obj, cell)
    }
  }

}
