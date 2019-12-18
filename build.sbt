import sbt.ExclusionRule
import sbtassembly.AssemblyPlugin.autoImport.MergeStrategy
name := "Datalake"

version := "0.1"

scalaVersion := "2.11.0"


libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.3.0" 
libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.3.0"
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.47"
libraryDependencies += "com.typesafe" % "config" % "1.3.1"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) =>  (xs map {_.toLowerCase}) match {
    case "services" :: xs => MergeStrategy.filterDistinctLines
    case _ => MergeStrategy.discard
  }
  case x => MergeStrategy.first
}
