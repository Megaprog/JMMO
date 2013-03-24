/*
 * Copyright (C) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.grid

import scala.collection.Set
import collection.immutable.TreeSet
import jmmo.util.Cell

/**
 * Adds single-level cache to AbstractGrid.
 * Cache is some other Grid with biggest cell size.
 * Each added or removed object smoother adds or removes to/from cache.
 * When it is make selection from cached grid by
 * `getObjectsInRectCell`, `getObjectsInRadiusCell` or `getObjectsInShape` methods
 * then use inner Grid with biggest cell size.
 *
 * This trait is abstract. It must be used some concrete child traits:
 * [[jmmo.grid.SingleLevelExactCache]] or
 * [[jmmo.grid.SingleLevelPosCache]]
 *
 * @author Tomas Shestakov
 * @tparam A type of object for adding to the Grid.
 */
trait SingleLevelCache[A] extends AbstractGrid[A] {

  /**
   * @return cell size of inner Grid used as cache
   */
  def firstLevelCacheCellSize: Int

  /**
   * @return new empty Grid for cache with cellSize equal firstLevelCacheCellSize
   */
  def makeGridForCache: AbstractGrid[A] = new AbstractGrid[A] with STArrayGridData[A]
                                                              with MTConcMutSetGridData[A]
                                                              with MTBlockingGridData[A] {
    def cellSize = firstLevelCacheCellSize
    def width = SingleLevelCache.this.width / firstLevelCacheCellSize
    def height = SingleLevelCache.this.height / firstLevelCacheCellSize

    //Convert small cells from AbstractGird to big cells for cacheGrid
    def objectCells(obj: A) =
      SingleLevelCache.this.objectCells(obj).map(cell => this.pointToCell(cell.asPoint2d))
        (jmmo.util.buildWith[Traversable[Cell], Cell, Set[Cell]](TreeSet.newBuilder[Cell]))
    /*(new CanBuildFrom[Traversable[Cell], Cell, Set[Cell]] {
      def apply(from: Traversable[Cell]) = apply()
      def apply() = new SetBuilder(scala.collection.mutable.Set.empty[Cell])
    })*/
  }

  protected val cacheGrid = makeGridForCache

  abstract override def addObject(obj: A) {
    super.addObject(obj)
    cacheGrid.addObject(obj)
  }

  abstract override def removeObject(obj: A) {
    super.removeObject(obj)
    cacheGrid.removeObject(obj)
  }
}

