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

object PrintableSyntax {
  implicit class PrintableOps[A](value: A) {
    def format(implicit print: Printable[A]): String = print.format(value)
    def print(implicit print: Printable[A]): Unit = println(print.format(value))
  }
}

object PrintableDemo extends App {
  import PrintableInstances._
  import PrintableSyntax._

  val cat = Cat("Felix", 42, "black and white")

  // print using Printable
  Printable.print(cat)

  // print using PrintableSyntax
  cat.print
}
