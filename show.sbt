import Show._
import sbt.Def
import sbt._



val title = taskKey[String]("title")
title := headline("Scary Build Tool?").value

val subtitle = taskKey[String]("sub")
subtitle := plain("Automate more with sbt!").value

val titleSection = taskKey[String]("section")

titleSection := subtitle.dependsOn(title).value




val introTitle = headline("intro")
val intro1 = bullet("I'm Justin, @ebenwert on Twitter")
val intro2 = bullet("my job is making it easier to build your project")
val intro3 = Def.task{} // placeholder
val intro4 = bullet("so I figured I'd build this talk")
val intro5 = bullet("you are actually watching a series of sbt tasks")
val introSection = taskKey[String]("intro")
introSection := intro5.dependsOn(intro4).dependsOn(intro3).dependsOn(intro2).dependsOn(intro1).dependsOn(introTitle).value

val dinoAscii = taskKey[String]("show a dinosaur")
dinoAscii := Def.taskDyn { plain(randomAscii.value) }.value

val motivationTitle = headline("motivation")
val motivation1 = bullet("sbt can be slow")
val motivation3 = bullet("So, a long, long time ago ...")
val motivation4 = dinoAscii
val motivation5 = bullet("we just wanted to make our builds less boring ...")
val motivation6 = bullet("it all started with dinosaurs")
val motivation6a = dinoAscii
val motivation7 = bullet("but there's more")
val motivation8 = bullet("sharing settings between all projects")
val motivation8a = bullet("repositories", 1)
val motivation8b = bullet("default dependencies", 1)
val motivation8c = bullet("code style", 1)
val motivation9 = bullet("automate more!")
val motivation9a = bullet("packaging")
val motivation9b = bullet("publishing")
val motivation9c = bullet("petty tasks")
val motivation9d = bullet("presentations")
val motivation9e = bullet("pleasant surprises")
val motivation9f = Dino.dinoChaseTask
val motivation10 = bullet("... scared yet?")

val motivationSection = taskKey[String]("motivation")
motivationSection :=
  (motivationTitle |> motivation1 |> motivation3 |> motivation4 |> motivation5 |>
    motivation6 |> motivation6a |> motivation7 |> motivation8 |> motivation8a |> motivation8b |> motivation8c |>
    motivation9 |> motivation9a |> motivation9b |> motivation9c |> motivation9d |> motivation9e |> motivation9f |>
    motivation10).value


val prepTitle = headline("preparation")
val prep1 = bullet("we're making our own plugin this session")
val prep2 = bullet("so I've automated the part where you have to look up in the documentation how to set one up")
val prep3 = bullet("so if you want to follow along, you can set it up right now")
val prep4 = bullet("install sbt 0.13.15:")
val prep5 = bullet("http://www.scala-sbt.org/download.html", 1)
val prep6 = bullet("sbt new sbt/sbt-autoplugin.g8")
val prepSection = taskKey[String]("prep")
prepSection := (prepTitle |> prep1 |> prep2 |> prep3 |> prep4 |> prep5 |> prep6).value

import Demographics._
demographicsSettings

val demographicsSection = taskKey[File]("demographics")
demographicsSection := (demographicsTitle |> demographics).value


val architectureTitle = headline("architecture")
val arch1 = bullet("the sbt shell interface consists of commands")
val arch2 = bullet("commands may change the 'build state'")
val arch3 = bullet("evaluated strictly sequentially", 1)
val arch4 = bullet("but I'm not getting into details of that today", 1)
val arch5 = bullet("more interesting: sbt has a fun setting and task system")
val arch6 = bullet("so let's have a look at how this presentation is implemented ...")
val architectureSection = taskKey[String]("architecture")
architectureSection := (architectureTitle |> arch1 |> arch2 |> arch3 |> arch4 |> arch5 |> arch6 |> dinoBike).value

val taskSystemTitle = headline("task system")
val ts1 = bullet("you've probably used it")
val ts2 = bullet("it's a bit weird")
val ts3 = bullet("what's up with .value ?!?")
val taskSystemSection = taskKey[String]("task system")
taskSystemSection := (taskSystemTitle |> ts1 |> ts2 |> ts3).value

val taskGraphTitle = headline("task graph")
val tg1 = bullet("when you run a task, a dependency graph is calculated")
val tg2 = bullet("a value for each task key is calculated only once")
val tg3 = bullet("independent tasks may run concurrently")
val tg4 = Def.task {
  dinoCircus.value
  dinoGun.value
  dinoCherry.value
  "all done!"
}
val taskGraphSection = taskKey[String]("task graph")
taskGraphSection := (taskGraphTitle |> tg1 |> tg2 |> tg3 |> tg4).value

val taskDependenciesTitle = headline("task dependencies")
val td1 = bullet("remember sbt 0.12 and all the crazy operators?")
val td2 = bullet("now, we have macros to express dependencies between tasks")
val td3 = bullet("why? sbt is not Scala")
val td4 = bullet("task may depend on many other tasks")
val td5 = bullet("it may depend on itself")
val td6 = bullet("but this is not recursion!",1)
val td7 = bullet("it's actually another node in the task graph")
val taskDependenciesSection = taskKey[String]("task dependencies")
taskDependenciesSection := (taskDependenciesTitle |> td1 |> td2 |> td3 |> td4 |> td5 |> td6 |> td7).value

val butWhyTitle = headline("but whyyyy? why not just plain functions?")
val bw1 = bullet("inversion of inversion of control?")
val bw2 = bullet("reuse")
val bw2a = bullet("results from tasks in different steps don't know about each other", 1)
val bw2b = bullet("instead of passing parameters and return values through the call stack, rely on a shared task result data structure", 1)
val bw2c = bullet("you don't want to modify that directly, too error-prone", 1)
val bw3 = Def.task(/*placeholder*/)
val bw4 = bullet("parallelization")
val bw4a = bullet("because why not?",1)
val bw4b = dinoMatrix
val bw5 = bullet("analysis")
val bw5a = bullet("sbt can tell us the dependencies between tasks", 1)
val bw5b = bullet("where does this weird result come from? play the inspect game!", 1)
val bw5c = bullet("maybe IntelliJ will be able to make this easier, too?", 1)
val bw6 = bullet("but how do you pass parameters?")
val bw6a = bullet("redefine the settings that tasks depend on",1)
val bw6b = bullet("anything is overridable",1)
val bw6c = Dino.dinoBikeTask

val butWhySection = taskKey[String]("but why?")
butWhySection :=
  (butWhyTitle |> bw1
    |> bw2 |> bw2a |> bw2b |> bw2c
    |> bw3
    |> bw4 |> bw4a |> bw4b
    |> bw5 |> bw5a |> bw5b |> bw5c
    |> bw6 |> bw6a |> bw6b |> bw6c).value

val practicalTitle = headline("let's get practical")
val practical1 = bullet("I'm too lazy to set up a github project every time I create a new repo")
val practical2 = bullet("it's rather easy to write a plugin, there might even be one that supports this ...")
val practical2a = bullet("http://www.scala-sbt.org/release/docs/Community-Plugins.html")
val practical3 = bullet("there isn't? Then let's start ...")
val practical4 = Dino.dinoRoarTask
val practical5 = bullet("so what is a plugin?")
val practical6 = bullet("sbt is recursive")
val practical6a = bullet("the build is built by another sbt build")
val practical6b = Def.task { quicklook((resourceDirectory in Compile).value / "yodawg-sbt.jpg") }
val practical6c = bullet("but in practice all recursion is at most three levels deep")
val practical6d = bullet("the build code is in the `project` project")
val practical6e = bullet("plus the .sbt files in the actual project")
val practical7 = bullet("a plugin is mostly just a library in the project project")

val practicalSection = taskKey[String]("let's get practical")
practicalSection :=
  (practicalTitle |> practical1 |> practical2 |> practical2a |> practical3 |> practical4 |> practical5 |>
    practical6 |> practical6a |> practical6b |> practical6c |> practical6d |> practical6e |> practical7 |>
    Dino.dinoGunTask).value

val presentation = taskKey[String]("complete presentation")
presentation :=
(titleSection |> introSection |> motivationSection |> demographicsSection |> architectureSection |>
  taskSystemSection |> taskGraphSection |> taskDependenciesSection |> butWhySection |> practicalSection |> dinoAscii).value

// TODO conclusion: sbt-github, publish plugin to github
