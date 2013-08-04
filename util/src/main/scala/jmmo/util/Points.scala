/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.util

import java.awt.Point

/**
 * Object for utilitarian methods
 *
 * @author Tomas Shestakov
 */
object Point2d {

  /**
   * Calculate distance between two points expressed by integer coordinates
   * @param x1 X coordinate of 1st point
   * @param y1 Y coordinate of 1st point
   * @param x2 X coordinate of 2nd point
   * @param y2 Y coordinate of 2nd point
   * @return distance between points in Double
   */
  def distance(x1: Int, y1: Int, x2: Int, y2: Int) = {
    val dx: Double = x2 - x1
    val dy: Double = y2 - y1
    scala.math.sqrt(dx * dx + dy * dy)
  }

  /**
   * Check if distance between two points less or equal specified length
   * @param x1 X coordinate of 1st point
   * @param y1 Y coordinate of 1st point
   * @param x2 X coordinate of 2nd point
   * @param y2 Y coordinate of 2nd point
   * @param length checking length between two points
   * @return true if distance between two points less or equal specified length, false otherwise
   */
  def distanceLessOrEqual(x1: Int, y1: Int, x2: Int, y2: Int, length: Int) = {
    val diameter = length * 2.0
    val normx = (x2 - x1) / diameter
    val normy = (y2 - y1) / diameter
    (normx * normx + normy * normy) <= 0.25
  }

  def compare(x1: Int, y1: Int, x2: Int, y2: Int): Int = {
    val res = (1000000000l * x1 + y1) - (1000000000l * x2 + y2)
    if (res > 0l)
      1
    else if (res < 0l)
      -1
    else
      0
  }

  //Implicits to convert from Point2d to java.awt.Point and back
  implicit def point2dToAwtPoint(point2d: Point2d): Point = new Point(point2d.x, point2d.y)

  implicit def awtPointToPoint2d(point: Point): Point2d = Point2d(point.x, point.y)
}

/**
 * A point representing a location in 2D coordinate space
 * @author Tomas Shestakov
 * @param x X coordinate of Point
 * @param y Y coordinate of Point
 */
case class Point2d(x: Int, y: Int) extends Ordered[Point2d] {

  /**
   * Calculate distance between this point and other point expressed by integer coordinates
   * @param x1 X coordinate of other point
   * @param y1 Y coordinate of other point
   * @return distance between points in Double
   */
  def distance(x1: Int, y1: Int) = {
    Point2d.distance(this.x, this.y, x1, y1)
  }

  /**
   * Calculate distance between this point and other point expressed by Point2d
   * @param other other Point
   * @return distance between points in Double
   */
  def distance(other: Point2d): Double = distance(other.x, other.y)

  /**
   * Check if distance between this and other point is less or equal specified length
   * @param x1 X coordinate of other point
   * @param y1 Y coordinate of other point
   * @param length checking length between two points
   * @return true if distance between this and other points less or equal specified length, false otherwise
   */
  def distanceLessOrEqual(x1: Int, y1: Int, length: Int): Boolean =
    Point2d.distanceLessOrEqual(this.x, this.y, x1, y1, length)

  /**
   * Check if distance between this and other point is less or equal specified length
   * @param other other Point
   * @param length checking length between two points
   * @return true if distance between this and other points less or equal specified length, false otherwise
   */
  def distanceLessOrEqual(other: Point2d, length: Int): Boolean = distanceLessOrEqual(other.x, other.y, length)

  /**
   * Calculate new Point2d with coordinates equal sum of coordinates this Point2d and specified x1 and y1
   * @param x1 X coordinate to summarize with `this.x`
   * @param y1 Y coordinate to summarize with `this.y`
   * @return new Point2d
   */
  def plus(x1: Int, y1: Int): Point2d = Point2d(this.x + x1, this.y + y1)

  /**
   * Calculate new Point2d with coordinates equal sum of coordinates this Point2d and other Point2d
   * @param other Point2d to summarize coordinates with this Point2d
   * @return new Point2d
   */
  def plus(other: Point2d): Point2d = plus(other.x, other.y)

  def compare(that: Point2d) = Point2d.compare(this.x, this.y, that.x, that.y)
}

/**
 * Cell address in a 2D Grid
 * @author Tomas Shestakov
 * @param cx X coordinate of Cell
 * @param cy Y coordinate of Cell
 */
case class Cell(cx: Int, cy: Int) extends Ordered[Cell] {

  /**
   * Calculate distance between this cell and other cell expressed by integer coordinates
   * @param cx1 X coordinate of other cell
   * @param cy1 Y coordinate of other cell
   * @return distance between cells in Double
   */
  def distance(cx1: Int, cy1: Int) = {
    Point2d.distance(this.cx, this.cy, cx1, cy1)
  }

  /**
   * Calculate distance between this point and other point expressed by Cell
   * @param other other Cell
   * @return distance between cells in Double
   */
  def distance(other: Cell): Double = distance(other.cx, other.cy)

  /**
   * Check if distance between this and other cell is less or equal specified length
   * @param cx1 X coordinate of other point
   * @param cy1 Y coordinate of other point
   * @param length checking length between two points
   * @return true if distance between this and other points less or equal specified length, false otherwise
   */
  def distanceLessOrEqual(cx1: Int, cy1: Int, length: Int): Boolean =
    Point2d.distanceLessOrEqual(this.cx, this.cy, cx1, cy1, length)

  /**
   * Check if distance between this and other cell is less or equal specified length
   * @param other other Point
   * @param length checking length between two points
   * @return true if distance between this and other points less or equal specified length, false otherwise
   */
  def distanceLessOrEqual(other: Cell, length: Int): Boolean = distanceLessOrEqual(other.cx, other.cy, length)

  /**
   * Calculate new Cell with coordinates equal sum of coordinates this Cell and specified cx and cy
   * @param cx1 X coordinate to summarize with `this.cx`
   * @param cy1 Y coordinate to summarize with `this.cy`
   * @return new Cell
   */
  def plus(cx1: Int, cy1: Int): Cell = Cell(this.cx + cx1, this.cy + cy1)

  /**
   * Calculate new Cell with coordinates equal sum of coordinates this Cell and other Cell
   * @param other Cell to summarize coordinates with this Cell
   * @return new Cell
   */
  def plus(other: Cell): Cell = plus(other.cx, other.cy)

  /**
   * @return Point2d with same coordinates as the Cell
   */
  def asPoint2d: Point2d = Point2d(cx, cy)

  def compare(that: Cell) = Point2d.compare(this.cx, this.cy, that.cx, that.cy)
}
