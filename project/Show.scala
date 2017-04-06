import sbt.Def.{Initialize, task}
import sbt.{Def, _}

import scala.util.Try

object Show {
  def headline(show: String): Def.Initialize[Task[String]] = task {
    println()
    println(show)
    println("-" * show.length)
    scala.Console.readLine()
    show
  }

  def plain(show: String): Def.Initialize[Task[String]] = task {
    println(show)
    scala.Console.readLine()
    show
  }

  def bullet(show: String, indent: Int = 0): Def.Initialize[Task[String]] = task {
    println("\t" * indent + "* " + show)
    scala.Console.readLine()
    show
  }

  def questionInt(show: String): Def.Initialize[Task[Int]] = task {
    println("-> " + show)
    def reallyWantAnInt: Int =
      Try(scala.Console.readInt()).recover {
        case _ =>
          println("come again? ")
          reallyWantAnInt
      }.getOrElse(0)
    reallyWantAnInt
  }

  implicit class Tasky[T](self: Initialize[Task[T]]) {
    def |>[U](other: Initialize[Task[U]]) = other.dependsOn(self)
    def before[U](other: Initialize[Task[U]]) = other.dependsOn(self)
  }

  def loop(vid: File, repeat: Int = 0, hPos: String = "50%", vPos: String = "50%"): String =
    s"mpv --border=no --ontop --loop-file=$repeat --geometry=$hPos:$vPos --really-quiet ${vid.getAbsolutePath}".!!

  def quicklook(lookat: File): String =
    Process(s"qlmanage -p -d1 ${lookat.getAbsolutePath}").!!(new ProcessLogger() {
      // just ignore stuff
      override def info(s: => String): Unit = ()
      override def error(s: => String): Unit = ()
      override def buffer[T](f: => T): T = f
    })

  def vid(filename: String) = Def.setting {
    (Keys.resourceDirectory in Compile).value / filename
  }
}

