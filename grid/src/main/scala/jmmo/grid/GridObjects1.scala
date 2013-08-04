/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import scala.collection.Set
import jmmo.util.Cell

/**
 * =Provide objects adding and removing abstractions for the Grid.=
 *
 * <p>Each added Object must have its Cells presentation which
 * consists from some number Cells occupied by Object
 * Object Cells presentation designate as function parameter: <code>cells: A => Traversable[Cell]</code>
 *
 * <p>We can plus some Objects to the Grid then:
 * check if an object is in a Cell or Rectangle or Radius
 * update Object which change its Cells presentation
 * remove Object from occupied them Cells.
 *
 * @author Tomas Shestakov
 *
 * @tparam A type of object for adding to the Grid.
 */
trait GridObjects1[A] {

  /**
   * Add an Object to the Grid.
   * @param obj an Object to plus
   * @param objToCells function to get Cells occupied by an Object
   * @return if object was not consisted in `objToCells` cells true, false otherwise
   */
  def addObject(obj: A)(implicit objToCells: A => Traversable[Cell]): Boolean

  /**
   * Remove an Object from the Grid.
   * @param obj an Object to remove
   * @param objToCells function to get Cells occupied by an Object
   * @return if object was consisted in `objToCells` cells true, false otherwise
   */
  def removeObject(obj: A)(implicit objToCells: A => Traversable[Cell]): Boolean

  /**
   * Update an Object in the Grid.
   * @param obj an Object to update
   * @param update function which update obj
   * @param objToCells function to get Cells occupied by an Object
   * @return if object was consisted in `objToCells` cells before updated and
   * not consisted in `objToCells` cells after update then true, false otherwise
   */
  def updateObject[U](obj: A, update: A => U)(implicit objToCells: A => Traversable[Cell]): Boolean

  /**
   * Get Objects which satisfy `filter` from Grid Cell.
   * @param cell Cell in the Grid Objects will be get from
   * @param filter a predicate used to test elements
   * @return Set of Objects
   */
  def getObjectsInCell(cell: Cell, filter: A => Boolean = null): Set[A]

  /**
   * Get Objects which satisfy `filter` predicate from Grid Cells bounded by a rectangle.
   * @param leftUpper left upper Cell
   * @param rightDown right down Cell
   * @param filter a predicate used to test elements
   * @return Set of Objects
   */
  def getObjectsInRect(leftUpper: Cell, rightDown: Cell, filter: A => Boolean = null): Set[A]

  /**
   * Get Objects which satisfy `filter` predicate from Grid Cells bounded by a circle with given radius.
   * @param center Cell in the center of the circle
   * @param radius radius of the circle
   * @param filter a predicate used to test elements
   * @return Set of Objects
   */
  def getObjectsInRadius(center: Cell, radius: Int, filter: A => Boolean = null): Set[A]

}
