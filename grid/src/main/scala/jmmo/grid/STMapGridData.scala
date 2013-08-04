/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import scala.collection._
import jmmo.util.Cell

/**
 * Single threaded Map based Grid data presentation trait.
 * @author Tomas Shestakov
 * @tparam A type of object for adding to the Grid.
 */
trait STMapGridData[A] extends STGridData[A] {

  protected val innerMap: scala.collection.mutable.Map[Cell, Set[A]] =
    new scala.collection.mutable.HashMap[Cell, Set[A]]

  protected override val innerData = new InnerData[A] {
    def apply(cell: Cell) = innerMap.get(cell) match {
      case None => null
      case Some(value) => value
    }

    def update(cell: Cell, set: Set[A]) {
      set match {
        case null => innerMap -= cell
        case value => innerMap(cell) = value
      }
    }
  }

}
