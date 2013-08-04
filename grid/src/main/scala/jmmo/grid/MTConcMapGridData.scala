/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import scala.collection.mutable.Map
import scala.collection.Set
import jmmo.util.Cell

/**
 * It is intended for mixing with [[jmmo.grid.MTBlockingGridData]]
 * instead of [[jmmo.grid.STMapGridData]] for using Grid in parallel.
 *
 * ConcurrentHashMap getting 1 concurrencyLevel because
 * only one thread will modify and all others will only read from ConcurrentHashMap.
 * @author Tomas Shestakov
 * @tparam A type of object for adding to the Grid.
 */
trait MTConcMapGridData[A] extends STMapGridData[A] {

  import collection.JavaConversions._

  //only one thread will modify and all others will only read from ConcurrentHashMap
  protected override val innerMap: Map[Cell, Set[A]] = new java.util.concurrent.ConcurrentHashMap[Cell, Set[A]](16, 0.75f, 1)

}
