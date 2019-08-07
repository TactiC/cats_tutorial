package nl.ronalddehaan.chapter_01

import cats._
import cats.implicits._

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

  // print using Printable object
  Printable.print(cat)

  // print using printable syntax
  cat.print


  // using cat's Show
  implicit val catShow: Show[Cat] = Show.show(c => s"${c.name} is a ${c.age} year old ${c.color} cat.")
  println(cat.show)


  val cat1 = Cat("Garfield", 38, "orange and black")
  val cat2 = Cat("Heathcliff", 38, "orange and black")

  val optionCat1 = Option(cat1)
  val optionCat2 = Option.empty[Cat]

  implicit val catEq: Eq[Cat] =
    Eq.instance[Cat] {
      (cat1, cat2) =>
          cat1.name  === cat2.name &&
          cat1.age   === cat2.age &&
          cat1.color === cat2.color
    }

  assert(!(cat1 === cat2))
  assert(cat1 =!= cat2)

  assert(!(optionCat1 === optionCat2))
  assert(optionCat1 =!= optionCat2)
}
