/*
 * Copyright 2011 Akira Ueda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import sbt._
import sbt.Keys._

object MahoutTest extends Build {

  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "net.physalis",
    scalaVersion := "2.9.1",
    scalacOptions ++= Seq("-Xcheckinit", "-encoding", "utf8")
  )

  val localResolver = "Local Maven Repository" at "file:///" + System.getProperty("user.home") + "/.m2/repository/"

  val loggingDependencies = Seq(
    "ch.qos.logback" % "logback-classic" % "0.9.25" withSources(),
    "org.codehaus.groovy" % "groovy" % "1.8.0" withSources(),
    "org.slf4j" % "slf4j-api" % "1.6.2" withSources(),
    "org.clapper" %% "grizzled-slf4j" % "0.6.6"
  )

  val mahoutVersion = "0.6-SNAPSHOT"

  val mahoutDependencies = Seq(
    "org.apache.mahout" % "mahout-core" % mahoutVersion intransitive,
    "org.apache.mahout" % "mahout-math" % mahoutVersion,
    "com.google.guava" % "guava" % "r09"
  )

  val testDependencies = Seq(
    "org.scalatest" %% "scalatest" % "1.6.1" % "test",
    "com.borachio" %% "borachio-scalatest-support" % "latest.integration" % "test"
  )

  lazy val root = Project("mia-study", file("."),
    settings = buildSettings ++ Seq(
      version := "0.1",
      resolvers += localResolver,
      libraryDependencies := mahoutDependencies ++ loggingDependencies ++ testDependencies
    )
  )

}

