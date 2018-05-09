package com.pedrorijo91.json

import java.lang.Thread._

import com.ning.http.client.AsyncHttpClientConfig
import com.typesafe.config.ConfigFactory
import play.api.libs.json.JsValue
import play.api.libs.json.Json.parse
import play.api.libs.ws.WS._
import play.api.libs.ws.WSRequestHolder
import play.api.libs.ws.ning.NingWSClient
import play.mvc.Http.HeaderNames

import scala.io.Source._


object Config {
  private val config = ConfigFactory.load("environments.conf")
  
  private val env = Option(System.getProperty("env")).getOrElse("qa")
  val desApi = config.getString(s"environments.$env.des.api")
  
  val environmentHeader: String = config.getString(s"environments.$env.des.environment-header")
  val authToken: String = config.getString(s"environments.$env.des.auth-token")
  val authTokenIncorrect = "Bearer100"
}

object Requests {
  
  import Config._
  
  implicit val wsClient = new NingWSClient(new AsyncHttpClientConfig.Builder().build())
  
  def newAckRef(): String = {
    val ref = System.currentTimeMillis % 1000000000L
    f"BRPYXX$ref%09d"
  }
  
  def buildRequest(url: String): WSRequestHolder = {
    clientUrl(url).withHeaders(
      "Environment" -> environmentHeader,
      HeaderNames.CONTENT_TYPE -> "application/json",
      "Authorization" -> authToken
    )
  }
  
  def buildInvalidRequest(url: String): WSRequestHolder = {
    clientUrl(url).withHeaders(
      "Environment" -> environmentHeader,
      HeaderNames.CONTENT_TYPE -> "application/json",
      "Authorization" -> authTokenIncorrect
    )
  }
  
  def buildMalformedRequestBody(sourceFileName: String, acknowledgementReference: String): String = {
    readFileAsString(sourceFileName)
  }
  
  def buildRequestBody(sourceFileName: String, acknowledgementReference: String): JsValue = {
    val templateStr = readFileAsString(sourceFileName)
    readFileAsString(sourceFileName)
    parse(templateStr.replaceAllLiterally("$AckRef$", acknowledgementReference))
  }
  
  private def readFileAsString(resourceName: String): String = {
    fromInputStream(currentThread().getContextClassLoader.getResourceAsStream(resourceName)).mkString
  }
}
