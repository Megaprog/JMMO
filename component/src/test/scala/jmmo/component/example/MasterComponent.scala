package jmmo.component.example

import jmmo.component.ComponentBase

/**
 * User: Tomas
 * Date: 19.09.13
 * Time: 13:11
 */
class MasterComponent extends ComponentBase[Master] with Master {

  def componentType = classOf[Master]

  def name = "ExampleMaster"
}
