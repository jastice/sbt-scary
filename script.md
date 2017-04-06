Scary Build Tool?
=================

Automate more with sbt!

## intro

* I'm Justin
* nice to meet you
* have you meet your fellow conference visitors yet?
* there's no talks after mine, so we have a moment to introduce ourselves
* say hi to someone sitting near you :)
* ...
* now that we're more familiar with each other ...
* I hope you are also ready to get more familiar with sbt


intro
-----
* I'm Justin, @ebenwert on Twitter
* my job is to make it easier to build your project
* working on the Scala plugin for IntelliJ
* improving sbt support
* I also like to automate my own job
* so I figured I'd "build" this talk, live
* you are actually watching a series of sbt tasks

motivation
----------
* sbt can be slow
* especially starting up and resolving
* So, a long, long time ago ...
* we just wanted to make my builds less boring ...

                           <\              _
                            \\          _/{
                     _       \\       _-   -_
                   /{        / `\   _-     - -_
                 _~  =      ( @  \ -        -  -_
               _- -   ~-_   \( =\ \           -  -_
             _~  -       ~_ | 1 :\ \      _-~-_ -  -_
           _-   -          ~  |V: \ \  _-~     ~-_-  -_
        _-~   -            /  | :  \ \            ~-_- -_
     _-~    -   _.._      {   | : _-``               ~- _-_
  _-~   -__..--~    ~-_  {   : \:}
=~__.--~~              ~-_\  :  /
                           \ : /__
                          //`Y'--\\
                         <+       \\
                          \\      WWW
                          MMM
* it all started with dinosaurs
* which is a totally worthwhile thing to use sbt for
* but there's more we can do
* share settings between many projects or modules of the same build
    * publish
    * internal repos, 
    * default dependencies
    * code style
* automate stuff!
    * packaging
        * sbt-native-packager
    * publishing
        * sbt-bintray
    * petty tasks you might do in bash scripts in other build systems
    * presentations
    * pleasant surprises!
    * (dino video) 
* scared yet?

demographics
------------
* I'm a bit interested who I'm talking to here

-> Who of you uses sbt at all?
-> Who uses plugins?
-> Who made their own plugin?
-> Who feels they understand how sbt actually works?

* so obviously, sbt tasks can collect input just fine
* usually less interactively

architecture
------------
* the sbt shell interface consists of commands
* commands may change the 'build state'
	* evaluated strictly sequentially
	* but I'm not getting into details of that today
* more interesting: sbt has a fun setting and task system
* so let's have a look at how this presentation is implemented ...

task system
-----------
* you've probably used it
* it's a bit weird
* what's up with .value ?!?

task graph
----------
* when you run a task, a dependency graph is calculated
* a value for each task key is calculated only once
* independent tasks may run concurrently
* <concurrent dinosaurs>
* live.sbt: look at what happened

task dependencies
-----------------
* remember sbt 0.12 and all the crazy operators?
* now, we have macros to express dependencies between tasks
* why? it turns out sbt is not (exactly) Scala
* task may depend on many other tasks
* it may depend on itself
	* but this is not recursion!
* it's actually another node in the task graph
* live.sbt: show task redefinition

but whyyyy? why not just plain functions?
-----------------------------------------
* inversion of inversion of control?
    * ioc is a fancy name for something taking parameters
    * sbt tasks don't usually take parameters
    * instead they declare their own dependencies
* reuse
	* results from tasks in different steps don't know about each other
	* instead of passing parameters and return values through the call stack, rely on a shared task result data structure
	* you don't want to modify that directly, too error-prone
* parallelization
	* because why not?
	* dino matrix
	* it does require tasks to not interfere with each other
* analysis
	* sbt can tell us the dependencies between tasks
	* where does this weird result come from? play the inspect game!
	* maybe IntelliJ will be able to make this easier, too?
* but how do you pass parameters?
	* (typically) redefine settings that tasks depend on
	* anything is overridable
	* <cycle dino>

let's get practical
-------------------
* I'm too lazy to set up a github project every time I create a new repo
* it's rather easy to write a plugin, there might even be one that supports this ...
* http://www.scala-sbt.org/release/docs/Community-Plugins.html
* there isn't? Then let's start ...
* <oops, dinosaur>
* I think it wants to remind me of something
* so what is a plugin?
* sbt is recursive
    * the build is built by another build
    * <yo dawg pic>
    * but in practice all recursion is at most three levels deep
    * the build code in the "project" project
    * and the .sbt files in the actual project
    * I've never seen an sbt build deeper than 2
* it's mostly just a library in the project project
* so let's start by simply copying the template there ...
