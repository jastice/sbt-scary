import sbt._
import Keys._

import scala.concurrent.duration._
import scala.concurrent.Await
import dispatch._
import dispatch.Defaults._
import spray.json._
import DefaultJsonProtocol._
import sbt.plugins.JvmPlugin

object GithubPlugin extends AutoPlugin {


  override def requires: Plugins = JvmPlugin
  override def trigger = allRequirements


}
