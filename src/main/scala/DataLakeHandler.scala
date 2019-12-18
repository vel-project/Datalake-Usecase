import java.util.Properties

import org.apache.spark.sql.{Dataset, Row, SparkSession}
import org.slf4j.LoggerFactory

object DataLakeHandler {
  val logger = LoggerFactory.getLogger(DataLakeHandler.getClass)
  System.setProperty("hadoop.home.dir", "F:\\avik\\winutils")

  var dataFrameMap: Map[String, Dataset[Row]] = Map()
  var connectionId: List[String] = List()
  connectionId = connectionId :+ "local"
  connectionId = connectionId :+ "mysql"

  def main(args: Array[String]): Unit = {

    try {
      val spark = SparkSession.builder.master("local[*]").getOrCreate
      for (id <- connectionId) {
        val props = Connections.getConnectionProperties(id)
        val rule = props.getProperty("query")
        val df = createDataset(id, props, spark).createOrReplaceTempView("src")
        var res = spark.sql(rule)
        dataFrameMap += (id -> res)
      }
      dataFrameMap("local").show
      dataFrameMap("mysql").show

    } catch {
      case ex: Exception => {
        throw ex
      }
    }
  }

  def createDataset(connectionId: String, props: Properties, spark: SparkSession): Dataset[Row] = {
    var df: Dataset[Row] = null
    connectionId match {
      case "local" => {
        df = spark.read.option("header", true).csv(props.getProperty("path"))
      }
      case "mysql" => {
        df = spark.read.jdbc(props.getProperty("url"), props.getProperty("tableName"), props)
      }
      case _ => {
        logger.error(" <Log and Throw Exceptions> ")
      }
    }
    df
  }
}
