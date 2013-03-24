/*
 * Copyright (C) 2012 Tomas Shestakov. <http://code.google.com/p/jmmo/>
 */

package jmmo

/**
 * Contains useful utilities.
 * @author Tomas Shestakov
 */
package object util {

  import scala.collection.generic.CanBuildFrom
  import scala.collection.mutable.Builder

  /**
   * Construct [[scala.collection.generic.CanBuildFrom]] from specified [[scala.collection.mutable.Builder]]
   * @param builder builder for new collection
   * @tparam From old collection type
   * @tparam Elem element type
   * @tparam To new collection type
   * @return class implemented [[scala.collection.generic.CanBuildFrom]] trait
   */
  def buildWith[From, Elem, To](builder: => collection.mutable.Builder[Elem, To]) =
    new CanBuildFrom[From, Elem, To] {
      def apply(from: From) = builder
      def apply() = builder
    }

  /**
   * Call apply method of `PartialFunction[A, Unit]`
   * @param func PartialFunction
   * @param x argument value of PartialFunction
   * @tparam A argument type of PartialFunction
   */
  def applyIfDefined[A](func: PartialFunction[A, Unit], x: A) {
    if (func.isDefinedAt(x)) {
      func.apply(x)
    }
  }

  /**
   * Returns `PartialFunction[A, Unit]` which define at non and do nothing
   * @tparam A argument type of PartialFunction
   * @return empty `PartialFunction[A, Unit]`
   */
  def emptyPartial[A]: PartialFunction[A, Unit] = innerEmptyPartial

  private val innerEmptyPartial = new PartialFunction[Any, Unit] {
    def isDefinedAt(x: Any) = false
    def apply(x: Any) { throw new UnsupportedOperationException("Calling apply() in empty partial function") }
  }

  /**
   * Check for AnyRef is not null
   * @param obj some AnyRef to check
   * @tparam A type of obj
   * @return passed reference value
   */
  def requireNonNull[A <: AnyRef](obj: A): A = {
    if (obj eq null) throw new IllegalArgumentException("reference value cannot be null")

    obj
  }

  /**
   * Check for AnyRef is not null
   * @param obj some AnyRef to check
   * @param message a String to include in the failure message
   * @tparam A type of obj
   * @return passed reference value
   */
  def requireNonNull[A <: AnyRef](obj: A, message: => Any): A = {
    if (obj eq null) throw new IllegalArgumentException("" + message)

    obj
  }

  /**
   * Returns default value same as class vars initialized by `= _` <br>
   * "It is 0 for numeric types, false for booleans, and null for reference types. This is the
   * same as if the same variable was deﬁned in Java without an initializer."
   * @tparam A type of desired default value
   * @return 0 for numeric types, false for booleans, and null for reference types.
   */
  def sysDefault[A] = {
    class Value {
      var v: A = _
    }
    (new Value).v
  }
}
