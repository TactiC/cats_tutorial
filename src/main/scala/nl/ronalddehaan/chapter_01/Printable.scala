package nl.ronalddehaan.chapter_01

trait Printable[A] {
  def format(value: A): String
}

final case class Cat(name: String, age: Int, color: String)

object PrintableInstances {
  implicit val stringPrinter: Printable[String] = (s: String) => s
  implicit val intPrinter: Printable[Int] = (i: Int) => i.toString

  implicit val catPrinter: Printable[Cat] = (c: Cat) =>
    s"${Printable.format(c.name)} is a ${Printable.format(c.age)} year old ${Printable.format(c.color)} cat."
}

object Printable {
  def format[A](value: A)(implicit printer: Printable[A]): String = printer.format(value)
  def print[A](value: A)(implicit printer: Printable[A]): Unit = println(format(value))
}
