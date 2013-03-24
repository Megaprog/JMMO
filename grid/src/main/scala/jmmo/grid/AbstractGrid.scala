/*
 * Copyright (C) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.grid

import scala.collection.Set
import java.awt.Shape
import jmmo.util.{Point2d, Cell}

/**
 * Some Grid which implements
 * [[jmmo.grid.GridDimension]] and
 * [[jmmo.grid.GridObjects]] traits.
 * Grid data access methods don't implemented here.
 * Ones implemented in
 * [[jmmo.grid.STArrayGridData]],
 * [[jmmo.grid.STMapGridData]],
 * [[jmmo.grid.MTBlockingGridData]],
 * and others traits.
 * @author Tomas Shestakov
 * @tparam A type of object for adding to the Grid.
 */
trait AbstractGrid[A] extends GridDimension with GridObjects[A] {

  def objectCells(obj: A): Traversable[Cell]

  def addObject(obj: A) {
    throw new UnsupportedOperationException("Not implemented here. Must be mixing with some child trait.")
  }

  def removeObject(obj: A) {
    throw new UnsupportedOperationException("Not implemented here. Must be mixing with some child trait.")
  }

  def updateObject[U](obj: A, update: (A) => U) {
    removeObject(obj)
    update(obj)
    addObject(obj)
  }

  protected val builderForSet = /*new CanBuildFrom[IndexedSeq[Int], A, Set[A]] {
                                  def apply(from: IndexedSeq[Int]) = apply()
                                  def apply() = scala.collection.mutable.Set.newBuilder
                                }*/
  jmmo.util.buildWith[IndexedSeq[Int], A, Set[A]](scala.collection.mutable.Set.newBuilder)

  def getObjectsInCell(cell: Cell, filter: A => Boolean = null): Set[A] = {
    //Returns null if cell is in the Grid or Set.empty if cell is out
    //Methods which overrides this must call super first
    //and returns its value if value not null or if super returns null calculate own one.
    if (isCellInGrid(cell))
      null
    else
      Set.empty
  }

  def getObjectsInPoint(point: Point2d, filter: (A) => Boolean = null) = getObjectsInCell(pointToCell(point), filter)

  def getObjectsInRectCell(leftUpper: Cell, rightDown: Cell, filter: A => Boolean = null): Set[A] =
    (leftUpper.cx to rightDown.cx).flatMap(cx =>
      (leftUpper.cy to rightDown.cy).flatMap(cy => getObjectsInCell(Cell(cx, cy), filter)))(builderForSet)

  def getObjectsInRect(leftUpper: Point2d, rightDown: Point2d, filter: (A) => Boolean = null) =
    (outerToInner(leftUpper.x) to outerToInner(rightDown.x)).withFilter(cx => {
      val px = innerToOuterInCenter(cx);
      leftUpper.x <= px && px <= rightDown.x
    }).flatMap(cx =>
      (outerToInner(leftUpper.y) to outerToInner(rightDown.y)).withFilter(cy => {
        val py = innerToOuterInCenter(cy);
        leftUpper.y <= py && py <= rightDown.y
      }).flatMap(cy =>
        getObjectsInCell(Cell(cx, cy), filter)))(builderForSet)

  def getObjectsInRadiusCell(center: Cell, radius: Int, filter: A => Boolean = null): Set[A] = {
    (center.cx - radius to center.cx + radius).flatMap(cx =>
      (center.cy - radius to center.cy + radius).withFilter(cy =>
        center.distanceLessOrEqual(cx, cy, radius)).flatMap(cy =>
        getObjectsInCell(Cell(cx, cy), filter)))(builderForSet)
  }

  def getObjectsInRadius(center: Point2d, radius: Int, filter: (A) => Boolean = null) = {
    (outerToInner(center.x - radius) to outerToInner(center.x + radius)).flatMap(cx =>
      (outerToInner(center.y - radius) to outerToInner(center.y + radius)).withFilter(cy =>
        center.distanceLessOrEqual(cellToPointInCenter(cx, cy), radius)).flatMap(cy =>
        getObjectsInCell(Cell(cx, cy), filter)))(builderForSet)
  }

  def getObjectsInShape(shape: Shape, filter: A => Boolean = null): Set[A] = {
    val bounds = shape.getBounds
    (outerToInner(bounds.x) to outerToInner(bounds.x + bounds.width - 1)).flatMap(cx =>
      (outerToInner(bounds.y) to outerToInner(bounds.y + bounds.height - 1)).withFilter(cy =>
        shape.contains(cellToPointInCenter(cx, cy))).flatMap(cy => getObjectsInCell(Cell(cx, cy), filter)))(builderForSet)
  }
}

