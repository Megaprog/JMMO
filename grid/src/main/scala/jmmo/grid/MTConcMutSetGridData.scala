/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import scala.collection.mutable.Set

/**
 * It is for mixing with [[jmmo.grid.MTBlockingGridData]]
 * instead of [[jmmo.grid.STMutSetGridData]] for using Grid in parallel
 * @author Tomas Shestakov
 * @tparam A type of object for adding to the Grid.
 */
trait MTConcMutSetGridData[A] extends STMutSetGridData[A] {

  protected abstract override def makeSet: Set[A] = {
    //make Concurrent Set for multi-threaded access
    import collection.JavaConversions._
    new java.util.concurrent.CopyOnWriteArraySet[A]()
  }

}
