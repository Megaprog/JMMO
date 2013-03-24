/*
 * Copyright (C) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo.grid

import scala.collection.Set
import java.awt.Shape
import jmmo.util.{Point2d, Cell}

/**
 * =Provide objects adding, removing and selection abstractions like a Grid.=
 *
 * <p>We can put some Objects to the Grid then:
 * check if an object is in a Cell or Rectangle or Radius
 * update Object which change its Cells presentation
 * remove Object from occupied them Cells.
 *
 * @author Tomas Shestakov
 *
 * @tparam A type of object for adding to the Grid.
 */
trait GridObjects[A] {

  /**
   * Add an Object to the Grid.
   * @param obj an Object to plus
   */
  def addObject(obj: A)

  /**
   * Remove an Object from the Grid.
   * @param obj an Object to remove
   */
  def removeObject(obj: A)

  /**
   * Update an Object in the Grid.
   * @param obj an Object to update
   * @param update function which updates the Object
   */
  def updateObject[U](obj: A, update: A => U)

  /**
   * Get Objects which satisfy `filter` from Grid Cell.
   * @param cell Cell in the Grid Objects will be get from
   * @param filter a predicate used to test elements
   * @return Set of Objects
   */
  def getObjectsInCell(cell: Cell, filter: A => Boolean = null): Set[A]

  /**
   * Get Objects which satisfy `filter` from Grid Cell contains specified Point2d.
   * @param point Point2d in the Grid Objects will be get from
   * @param filter a predicate used to test elements
   * @return Set of Objects
   */
  def getObjectsInPoint(point: Point2d, filter: A => Boolean = null): Set[A]

  /**
   * Get Objects which satisfy `filter` predicate from Grid bounded by a rectangle specified in Cells.
   * @param leftUpper left upper Cell
   * @param rightDown right down Cell
   * @param filter a predicate used to test elements
   * @return Set of Objects
   */
  def getObjectsInRectCell(leftUpper: Cell, rightDown: Cell, filter: A => Boolean = null): Set[A]

  /**
   * Get Objects which satisfy `filter` predicate from Grid bounded by a rectangle specified in Points.
   * @param leftUpper left upper Point2d
   * @param rightDown right down Point2d
   * @param filter a predicate used to test elements
   * @return Set of Objects
   */
  def getObjectsInRect(leftUpper: Point2d, rightDown: Point2d, filter: A => Boolean = null): Set[A]

  /**
   * Get Objects which satisfy `filter` predicate from Grid bounded by a circle with given radius in Cells.
   * @param center Cell in the center of the circle
   * @param radius radius of the circle in Cells
   * @param filter a predicate used to test elements
   * @return Set of Objects
   */
  def getObjectsInRadiusCell(center: Cell, radius: Int, filter: A => Boolean = null): Set[A]

  /**
   * Get Objects which satisfy `filter` predicate from Grid bounded by a circle with given radius in Points.
   * @param center Point2d in the center of the circle
   * @param radius radius of the circle in Points
   * @param filter a predicate used to test elements
   * @return Set of Objects
   */
  def getObjectsInRadius(center: Point2d, radius: Int, filter: A => Boolean = null): Set[A]

  /**
   * Get Objects which satisfy `filter` predicate from Grid bounded by a [[java.awt.Shape]].
   * Shape coordinates must be in outer (points) coordinates system.
   * @param shape a Shape contains some Cells
   * @param filter a predicate used to test elements
   * @return Set of Objects
   */
  def getObjectsInShape(shape: Shape, filter: A => Boolean = null): Set[A]
}
