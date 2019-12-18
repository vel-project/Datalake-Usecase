import java.util.Properties

import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

object Connections  {

  val logger = LoggerFactory.getLogger(Connections.getClass)

  val configurationFile = ConfigFactory.load()

  def getConnectionProperties(connectionId: String): Properties = {
    val props = new Properties
    connectionId match {
      case "local" => {
        props.setProperty("path", configurationFile.getString("local.path"))
        props.setProperty("query", configurationFile.getString("local.rule.query"))
      }
      case "mysql" => {
        props.setProperty("user", configurationFile.getString("mysql.user"))
        props.setProperty("password", configurationFile.getString("mysql.password"))
        props.setProperty("tableName", configurationFile.getString("mysql.tableName"))
        props.setProperty("driver", configurationFile.getString("mysql.driver"))
        props.setProperty("url", configurationFile.getString("mysql.url"))
        props.setProperty("query", configurationFile.getString("mysql.rule.query"))
      }
      case _ => {
        logger.error(" <Log and Throw Exceptions> ")
      }
    }
    props
  }
}
