package jmmo.component

/**
 * User: Tomas
 * Date: 21.09.13
 * Time: 22:07
 */
trait DepComponent[A] extends Component[A] {

  def require: collection.Set[Class[_]]
}
