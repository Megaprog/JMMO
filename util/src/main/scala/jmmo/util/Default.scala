package jmmo.util

/**
 * Coded by [[http://stackoverflow.com/users/192247/missingfaktor]] <br>
 * The trait Default is a typeclass, which is invariant on A for obvious reasons.
 * Its companion object Default contains implicits and implicit suppliers for a few common types.
 * The method forAnyRef is put in a separate trait LowPriorityImplicitsForDefault so that
 * it has a lower precedence than other more specific implicit supplier methods such as forNumeric
 * (We do this to hack around Scala's implicit lookup rules). <br>
 * Трейт вместе с объектом компаньоном служит для получения значения по умолчанию в соответствии
 * с заданными типом. <br>
 * This trait with the companion object is to get the default values ​​in according to the specified type.<br>
 * @tparam A
 * @author missingfaktor
 */
trait Default[A] {
  def value: A
}

trait LowPriorityImplicitsForDefault { this: Default.type =>
  implicit def forAnyRef[A](implicit ev: Null <:< A) = Default withValue (null : A)
}

/**
 * This object is to get the default values ​​in according to the specified type.<br>
 * Contains implicits with default values for for some types.
 * You can add new implicitis to getting default values for other types.
 */
object Default extends LowPriorityImplicitsForDefault {
  def withValue[A](a: A) = new Default[A] {
    def value = a
  }

  implicit val forBoolean = Default withValue false
  implicit val forChar = Default withValue ' '
  implicit def forNumeric[A](implicit n: Numeric[A]) = Default withValue n.zero
  implicit val forString = Default withValue ""
  implicit def forOption[A] = Default withValue (None : Option[A])

  /**
   * @tparam A type of desired default value
   * @return Default value for specified type.
   */
  def default[A : Default] = implicitly[Default[A]].value
}