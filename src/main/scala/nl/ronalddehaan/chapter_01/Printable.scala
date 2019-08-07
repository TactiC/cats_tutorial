package nl.ronalddehaan.chapter_01

trait Printable[A] {
  def format(value: A): String
}

object PrintableInstances {
  implicit val stringPrinter: Printable[String] = (s: String) => s
  implicit val intPrinter: Printable[Int] = (i: Int) => i.toString
}

object Printable {
  def format[A](value: A)(implicit printer: Printable[A]): String = printer.format(value)
  def print[A](value: A)(implicit printer: Printable[A]): Unit = println(format(value))
}
