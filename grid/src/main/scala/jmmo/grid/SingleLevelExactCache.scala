/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import scala.collection.Set
import java.awt.{Rectangle, Shape}
import jmmo.util.{Point2d, Cell}

/**
 * Adds single-level cache to AbstractGrid.
 * Cache is some other Grid with biggest cell size.
 * Each added or removed object smoother adds or removes to/from cache.
 * When it is make selection from cached grid by
 * `getObjectsInRectCell`, `getObjectsInRadiusCell` or `getObjectsInShape` methods
 * then use inner Grid with biggest cell size.
 *
 * Trait for mixing with main group of AbstractGrid traits.
 *
 * SingleLevelExactCache get objects from cache only
 * if Cache Grid Cells completely included by area of selection.
 * Cells in main Grid which not contains in Cache Grid Cells are handled ordinary.
 * @author Tomas Shestakov
 * @tparam A type of object for adding to the Grid.
 */
trait SingleLevelExactCache[A] extends SingleLevelCache[A] {

  /**
   * Tests if using of cache is meaning
   * @param luX left upper X coordinate
   * @param luY left upper Y coordinate
   * @param rdX right down X coordinate
   * @param rdY right down Y coordinate
   * @return true if it is needed use cache, false otherwise
   */
  protected def isCacheNeeded(luX: Int, luY: Int, rdX: Int, rdY: Int) = {
    val neededSize = innerToOuter(cacheGrid.cellSize * 2)
    (rdX - luX + 1 >= neededSize) && (rdY - luY + 1 >= neededSize)
  }

  /**
   * Calculate bounds of cache by outer bounds and inside function check cached rectangle is in shape
   * @param luX left upper X coordinate of outer bounds
   * @param luY left upper Y coordinate of outer bounds
   * @param rdX right down X coordinate of outer bounds
   * @param rdY right down Y coordinate of outer bounds
   * @param inside function check rectangle is in shape
   * @return tuple with left upper, right down Cells to cache from Grid for cache and Rectangle bounds caching area
   */
  private def calcCacheBounds(luX: Int, luY: Int, rdX: Int, rdY: Int, inside: (Int, Int, Int, Int) => Boolean) = {
    //the center of cache area is
    val centerX = (luX + rdX) / 2
    val centerY = (luY + rdY) / 2
    //How much points are in cacheGrid one cell
    val cacheMult = cellSize * cacheGrid.cellSize

    var cacheLuX = centerX / cacheMult
    var cacheLuY = centerY / cacheMult
    var cacheRdX = cacheLuX
    var cacheRdY = cacheLuY
    var cacheLuXabs = cacheLuX * cacheMult
    var cacheLuYabs = cacheLuY * cacheMult
    var cacheRdXabs = cacheLuXabs + cacheMult - 1
    var cacheRdYabs = cacheLuYabs + cacheMult - 1

    def isAreaInBounds(luXabs: Int, luYabs: Int, rdXabs: Int, rdYabs: Int) =
    //check area is in grid
      0 <= luXabs && rdXabs <= outerWidth && 0 <= luYabs && rdYabs <= outerHeight &&
        //check area is in shape
        inside(luXabs, luYabs, rdXabs, rdYabs)

    if (isAreaInBounds(cacheLuXabs, cacheLuYabs, cacheRdXabs, cacheRdYabs)) {

      while (isAreaInBounds(cacheLuXabs - cacheMult, cacheLuYabs, cacheRdXabs, cacheRdYabs)) {
        cacheLuX -= 1
        cacheLuXabs -= cacheMult
      }
      while (isAreaInBounds(cacheLuXabs, cacheLuYabs - cacheMult, cacheRdXabs, cacheRdYabs)) {
        cacheLuY -= 1
        cacheLuYabs -= cacheMult
      }
      while (isAreaInBounds(cacheLuXabs, cacheLuYabs, cacheRdXabs + cacheMult, cacheRdYabs)) {
        cacheRdX += 1
        cacheRdXabs += cacheMult
      }
      while (isAreaInBounds(cacheLuXabs, cacheLuYabs, cacheRdXabs, cacheRdYabs + cacheMult)) {
        cacheRdY += 1
        cacheRdYabs += cacheMult
      }

     (true, Cell(cacheLuX, cacheLuY), Cell(cacheRdX, cacheRdY), new Rectangle(
                                                                  cacheGrid.innerToOuter(cacheLuX),
                                                                  cacheGrid.innerToOuter(cacheLuY),
                                                                  cacheGrid.innerToOuter(cacheRdX - cacheLuX + 1),
                                                                  cacheGrid.innerToOuter(cacheRdY - cacheLuY + 1)))
    }
    else {
      (false, null, null, null)
    }
  }

  abstract override def getObjectsInRectCell(leftUpper: Cell, rightDown: Cell, filter: A => Boolean = null): Set[A] =
    getObjectsInRect(cellToPointInCenter(leftUpper), cellToPointInCenter(rightDown), filter)

  abstract override def getObjectsInRect(leftUpper: Point2d, rightDown: Point2d, filter: (A) => Boolean = null): Set[A] = {
    val (needed, luCacheCell, rdCacheCell, rectCache) =
      calcCacheBounds(leftUpper.x, leftUpper.y, rightDown.x, rightDown.y, (lux: Int, luy: Int, rdx: Int, rdy: Int) =>
        leftUpper.x <= lux && rdy <= rightDown.x && leftUpper.y <= luy && rdy <= leftUpper.x)
    if (needed) {
      (outerToInner(leftUpper.x) to outerToInner(rightDown.x)).withFilter(cx => {
        val px = innerToOuterInCenter(cx);
        leftUpper.x <= px && px <= rightDown.x
      }).flatMap(cx =>
        (outerToInner(leftUpper.y) to outerToInner(rightDown.y)).withFilter(cy => {
          val py = innerToOuterInCenter(cy);
          leftUpper.y <= py && py <= rightDown.y &&
            !rectCache.contains(cx, cy)
        }).flatMap(cy =>
          getObjectsInCell(Cell(cx, cy), filter)))(builderForSet) ++
        cacheGrid.getObjectsInRectCell(luCacheCell, rdCacheCell, filter)
    }
    else {
      super.getObjectsInRect(leftUpper, rightDown, filter)
    }
  }

  abstract override def getObjectsInRadiusCell(center: Cell, radius: Int, filter: A => Boolean = null): Set[A] =
    getObjectsInRadius(cellToPointInCenter(center), innerToOuter(radius), filter)

  abstract override def getObjectsInRadius(center: Point2d, radius: Int, filter: (A) => Boolean = null): Set[A] = {
    val (needed, luCacheCell, rdCacheCell, rectCache) =
      calcCacheBounds(center.x - radius, center.y - radius, center.x + radius, center.y + radius,
        (lux: Int, luy: Int, rdx: Int, rdy: Int) =>
          center.distanceLessOrEqual(lux, luy, radius) &&
            center.distanceLessOrEqual(lux, rdy, radius) &&
            center.distanceLessOrEqual(rdx, luy, radius) &&
            center.distanceLessOrEqual(rdx, rdy, radius))
    if (needed) {
      (outerToInner(center.x - radius) to outerToInner(center.x + radius)).flatMap(cx =>
        (outerToInner(center.y - radius) to outerToInner(center.y + radius)).withFilter(cy =>
          !rectCache.contains(cx, cy) && center.distanceLessOrEqual(cellToPointInCenter(cx, cy), radius)).flatMap(cy =>
          getObjectsInCell(Cell(cx, cy), filter)))(builderForSet) ++
        cacheGrid.getObjectsInRectCell(luCacheCell, rdCacheCell, filter)
    }
    else {
      super.getObjectsInRadius(center, radius, filter)
    }
  }

  abstract override def getObjectsInShape(shape: Shape, filter: A => Boolean = null): Set[A] = {
    val bounds = shape.getBounds
    val (needed, luCacheCell, rdCacheCell, rectCache) =
      calcCacheBounds(bounds.x, bounds.y, bounds.x + bounds.width - 1, bounds.y + bounds.height - 1,
        (lux: Int, luy: Int, rdx: Int, rdy: Int) =>
          shape.contains(lux, luy, rdx - lux + 1, rdy - lux + 1))
    if (needed) {
      (outerToInner(bounds.x) to outerToInner(bounds.x + bounds.width - 1)).flatMap(cx =>
        (outerToInner(bounds.y) to outerToInner(bounds.y + bounds.height - 1)).withFilter(cy =>
          !rectCache.contains(cx, cy) && shape.contains(cellToPointInCenter(cx, cy).toAwtPoint)).flatMap(cy =>
          getObjectsInCell(Cell(cx, cy), filter)))(builderForSet) ++
        cacheGrid.getObjectsInRectCell(luCacheCell, rdCacheCell, filter)
    }
    else {
      super.getObjectsInShape(shape, filter)
    }
  }

}

