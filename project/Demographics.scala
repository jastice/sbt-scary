import Show.{headline, questionInt}
import sbt.Keys._
import sbt._

case class Demographics(usesSbt: Int, usesPlugins: Int, madePlugin: Int, understandsSbt: Int) {
}

object Demographics {

  val demographicsTitle = taskKey[String]("demographicsTitle")
  val usesSbt = taskKey[Int]("uses sbt?")
  val usesPlugins = taskKey[Int]("uses plugins?")
  val madePlugin = taskKey[Int]("made plugin?")
  val understandsSbt = taskKey[Int]("understands sbt?")
  val poll = taskKey[Int]("poll in order")
  val demographics: TaskKey[File] = taskKey[File]("demographics poll")
  val demographicsCode = taskKey[String]("demographics generated code")

  val demographicsSettings = Seq(
    poll := understandsSbt.dependsOn(madePlugin).dependsOn(usesPlugins).dependsOn(usesSbt).value,
    demographicsTitle := headline("demographics").value,
    usesSbt := questionInt("Who of you uses sbt at all?").value,
    usesPlugins := questionInt("Who uses plugins?").value,
    madePlugin := questionInt("Who made their own plugin?").value,

    understandsSbt := {
      val understands = questionInt("Who feels they understand how sbt actually works?").value
      if (understands > 0) println("Maybe you should be giving this workshop?")
      else println("(nor do I)")
      understands
    },

    demographicsCode := {
      s"""
        |package demo
        |
        |case class Demographics(usesSbt: Int, usesPlugins: Int, madePlugin: Int, understandsSbt: Int)
        |object Demographics extends App {
        |  val generated = Demographics(${usesSbt.value}, ${usesPlugins.value}, ${madePlugin.value}, ${understandsSbt.value})
        |  println("generated Demographics: " + generated)
        |}
      """.stripMargin
    },

    demographics := Def.task {
      val dem = Demographics(usesSbt.value, usesPlugins.value, madePlugin.value, understandsSbt.value)

      val demographicsFile = (scalaSource in Compile).value / "demographics.scala"
      IO.write(demographicsFile, demographicsCode.value)
      println(demographicsCode.value)
      println("demographics written to " + demographicsFile)

      demographicsFile
    }.dependsOn(poll).value
  )


}