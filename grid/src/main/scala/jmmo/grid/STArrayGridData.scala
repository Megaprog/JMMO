/*
 * Copyright (C) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.grid

import scala.collection.Set
import jmmo.util.Cell

/**
 * Single threaded Array based Grid data presentation trait.
 * @author Tomas Shestakov
 * @tparam A type of object for adding to the Grid.
 */
trait STArrayGridData[A] extends STGridData[A] {

  protected val innerArray = Array.ofDim[Set[A]](width, height)

  protected override val innerData = new InnerData[A] {
    def apply(cell: Cell) = innerArray(cell.cx)(cell.cy)

    def update(cell: Cell, set: Set[A]) {
      innerArray(cell.cx)(cell.cy) = set
    }
  }

}
