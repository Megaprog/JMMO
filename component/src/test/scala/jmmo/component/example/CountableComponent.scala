package jmmo.component.example

import jmmo.component.ComponentBase

/**
 * User: Tomas
 * Date: 19.09.13
 * Time: 13:11
 */
class CountableComponent extends ComponentBase[Countable] with Countable {

  def componentType: Class[Countable] = classOf[Countable]

  def count: Int = 1

  def run() {}
}
