/*
 * Copyright (C) 2013 Tomas Shestakov. <https://github.com/Megaprog/JMMO>
 */

package jmmo.grid

/**
 * Abstract class for testing different Grid framework traits using [[jmmo.grid.Dummy]] object
 * @param cellSize size of one Cell in the Grid
 * @param width width of the Grid in cells
 * @param height height of the Grid in cells
 * @author Tomas Shestakov
 */
abstract class TestGrid(val cellSize: Int, val width: Int, val height: Int) extends AbstractGrid[Dummy] {

  def objectCells(obj: Dummy) = obj.getCells(this)
}
