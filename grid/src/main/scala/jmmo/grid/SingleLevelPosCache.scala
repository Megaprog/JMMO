/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import scala.collection.Set
import java.awt.Shape
import jmmo.util.{Cell, Point2d}

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
 * SingleLevelPosCache get all objects from cache and then check
 * coordinates of selected objects if it hit area of selection.
 *
 * Grid mixing with SingleLevelPosCache can not meet AbstractGridSpec
 * and its depended specifications
 * @author Tomas Shestakov
 * @tparam A type of object for adding to the Grid.
 */
trait SingleLevelPosCache[A] extends SingleLevelCache[A] {

  def objectPosition(obj: A): Point2d

  /**
   * Calculate bounds of cache
   * @param luX left upper X coordinate
   * @param luY left upper Y coordinate
   * @param rdX right down X coordinate
   * @param rdY right down Y coordinate
   * @return tuple with left upper, right down Cells to cache from Grid for cache and Rectangle bounds caching area
   */
  private def calcCacheBounds(luX: Int, luY: Int, rdX: Int, rdY: Int) = {
    val luCacheCell = cacheGrid.pointToCell(pointToCell(luX, luY).asPoint2d)
    val rdCacheCell = cacheGrid.pointToCell(pointToCell(rdX, rdY).asPoint2d)

    (luCacheCell, rdCacheCell)
  }

  /**
   * Calculate one filter from twice
   * @param f1 1st filter must be not null
   * @param f2 2nd filter can be null
   * @return filter look like f1 && f2
   */
  private def dualFilter(f1: A => Boolean, f2: A => Boolean) = {
    if (f2 == null)
      f1
    else
      (obj: A) => f1(obj) && f2(obj)
  }

  abstract override def getObjectsInRectCell(leftUpper: Cell, rightDown: Cell, filter: A => Boolean = null): Set[A] =
    getObjectsInRect(cellToPointInCenter(leftUpper), cellToPointInCenter(rightDown), filter)

  abstract override def getObjectsInRect(leftUpper: Point2d, rightDown: Point2d, filter: (A) => Boolean = null): Set[A] = {
    val (luCacheCell, rdCacheCell) = calcCacheBounds(leftUpper.x, leftUpper.y, rightDown.x, rightDown.y)
    val rectFilter = (obj: A) => {
      val pos = objectPosition(obj)
      leftUpper.x <= pos.x && pos.x <= rightDown.x && leftUpper.y <= pos.y && pos.y <= rightDown.y
    }
    cacheGrid.getObjectsInRectCell(luCacheCell, rdCacheCell, dualFilter(rectFilter, filter))
  }

  abstract override def getObjectsInRadiusCell(center: Cell, radius: Int, filter: A => Boolean = null): Set[A] =
    getObjectsInRadius(cellToPointInCenter(center), innerToOuter(radius), filter)

  abstract override def getObjectsInRadius(center: Point2d, radius: Int, filter: (A) => Boolean = null): Set[A] = {
    val (luCacheCell, rdCacheCell) = calcCacheBounds(center.x - radius, center.y - radius, center.x + radius, center.y + radius)

    cacheGrid.getObjectsInRectCell(luCacheCell, rdCacheCell, dualFilter(obj =>
      center.distanceLessOrEqual(objectPosition(obj), radius), filter))
  }

  abstract override def getObjectsInShape(shape: Shape, filter: A => Boolean = null): Set[A] = {
    val bounds = shape.getBounds
    val (luCacheCell, rdCacheCell) = calcCacheBounds(bounds.x, bounds.y, bounds.x + bounds.width - 1, bounds.y + bounds.height - 1)

    cacheGrid.getObjectsInRectCell(luCacheCell, rdCacheCell, dualFilter(obj =>
      shape.contains(objectPosition(obj).toAwtPoint), filter))
  }

}

