/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

import jmmo.util.{Cell, Point2d}

/**
 * Provide `width`, `height` and `cellSize` abstractions for the Grid.
 *
 * The Grid consist some cells in numerical terms "width" by horizontal and
 * some cells in numerical terms "height" by vertical.
 *
 * Each cell consist "cellSize" of points by horizontal and vertical.
 *
 * @author Tomas Shestakov
 */
trait GridDimension {

  /**
   * @return size of one cell of the Grid
   */
  def cellSize: Int

  /**
   * @return width of the Grid as number of cells by horizontal
   */
  def width: Int

  /**
   * @return height of the Grid as number of cells by vertical
   */
  def height: Int

  /**
   * @return half of the Cell size
   */
  def halfCell: Int = cellSize / 2

  /**
   * @return width of the Grid as number of points by horizontal
   */
  def outerWidth: Int = innerToOuter(width)

  /**
   * @return height of the Grid as number of points by vertical
   */
  def outerHeight: Int = innerToOuter(height)

  /**
   * Converts outer (point) coordinate to inner (cell) coordinate
   * @param outerValue number points for which will calculated number covered by them cells
   * @return number cells which is covered by `outerValue` points
   */
  def outerToInner(outerValue: Int): Int = outerValue / cellSize

  /**
   * Converts inner (cell) coordinate to outer (point) coordinate
   * @param innerValue number cells for which will calculated number points to cover them
   * @return number points to cover `innerValue` cells
   */
  def innerToOuter(innerValue: Int): Int = innerValue * cellSize

  /**
   * Converts inner (cell) coordinate to outer (point) center coordinate.
   * It is equals innerToOuter(innerValue) + cellSize/2
   * @param innerValue number cells for which will calculated number points to cover them
   * @return number points to cover `innerValue` cells
   */
  def innerToOuterInCenter(innerValue: Int): Int = innerValue * cellSize + halfCell

  /**
   * Converts outer Point2d to inner Cell location in the Grid
   * @param point a Point2d to convert
   * @return the Cell consists a Point2d
   */
  def pointToCell(point: Point2d): Cell = Cell(outerToInner(point.x), outerToInner(point.y))

  /**
   * Converts outer Point2d (specified by coordinates) to inner Cell location in the Grid
   * @param px X coordinate of Point2d to convert
   * @param py Y coordinate of Point2d to convert
   * @return the Cell consists a Point2d
   */
  def pointToCell(px: Int, py: Int): Cell = Cell(outerToInner(px), outerToInner(py))

  /**
   * Converts inner Cell to outer Point2d location in the Grid
   * (result Point2d will has the least coordinates in a Cell)
   * @param cell a Cell to convert
   * @return the Point2d has the least coordinates in a Cell
   */
  def cellToPoint(cell: Cell): Point2d = Point2d(innerToOuter(cell.cx), innerToOuter(cell.cy))

  /**
   * Converts inner Cell (specified by coordinates) to outer Point2d location in the Grid
   * (result Point2d will has the least coordinates in a Cell)
   * @param cx X coordinate of Cell to convert
   * @param cy Y coordinate of Cell to convert
   * @return the Point2d has the least coordinates in a Cell
   */
  def cellToPoint(cx: Int, cy: Int): Point2d = Point2d(innerToOuter(cx), innerToOuter(cy))

  /**
   * Converts inner Cell to outer Point2d location in the Grid
   * (result Point2d will has coordinates of the center in a Cell)
   * @param cell a Cell to convert
   * @return the Point2d has coordinates of the center in a Cell
   */
  def cellToPointInCenter(cell: Cell): Point2d =
    Point2d(innerToOuterInCenter(cell.cx), innerToOuterInCenter(cell.cy))

  /**
   * Converts inner Cell (specified by coordinates) to outer Point2d location in the Grid
   * (result Point2d will has coordinates of the center in a Cell)
   * @param cx X coordinate of Cell to convert
   * @param cy Y coordinate of Cell to convert
   * @return the Point2d has coordinates of the center in a Cell
   */
  def cellToPointInCenter(cx: Int, cy: Int): Point2d =
    Point2d(innerToOuterInCenter(cx), innerToOuterInCenter(cy))

  /**
   * Checks if a Point is inside the Grid
   * @param point a Point2d to check
   * @return true if a Point is inside the Grid, else otherwise
   */
  def isPointInGrid(point: Point2d): Boolean = isPointInGrid(point.x, point.y)

  /**
   * Checks if a Point (specified by coordinates) is inside the Grid
   * @param px X coordinate of Point2d to check
   * @param py Y coordinate of Point2d to check
   * @return true if a Point is inside the Grid, else otherwise
   */
  def isPointInGrid(px: Int, py: Int): Boolean =
    (px >= 0 && px < outerWidth) && (py >= 0 && py < outerHeight)

  /**
   * Checks if a Cell is inside the Grid
   * @param cell a Cell to check
   * @return true if a Cell is inside the Grid, else otherwise
   */
  def isCellInGrid(cell: Cell): Boolean = isCellInGrid(cell.cx, cell.cy)

  /**
   * Checks if a Cell is inside the Grid
   * @param cx X coordinate of Cell to check
   * @param cy Y coordinate of Cell to check
   * @return true if a Cell is inside the Grid, else otherwise
   */
  def isCellInGrid(cx: Int, cy: Int): Boolean =
    (cx >= 0 && cx < width) && (cy >= 0 && cy < height)

  /**
   * Checks if two Points is inside same cell
   * @param point1 1st Point2d
   * @param point2 2nd Point2d
   * @return true if one Cell consist both Points, else otherwise
   */
  def inSameCell(point1: Point2d, point2: Point2d): Boolean = {
    pointToCell(point1) == pointToCell(point2)
  }

}
