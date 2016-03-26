package com.pedrorijo91.json

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._

/*******************  STEP 1  ********************/
case class UserV1(username: String, friends: Int, enemies: Int, isAlive: Boolean)

object UserV1 {
  implicit val userJsonFormat = Json.format[UserV1]
}

/*******************  STEP 2  ********************/
case class UserV2(username: String, friends: Int, enemies: Int, private val is_alive: Boolean) {
  def isAlive: Boolean = is_alive
}

object UserV2 {
  implicit val userJsonFormat = Json.format[UserV2]
}

/*******************  STEP 3  ********************/
case class UserV3(username: String, friends: Int, enemies: Int, isAlive: Boolean)

object UserV3 {

  import play.api.libs.functional.syntax._
  import play.api.libs.json._

  implicit val userReads: Reads[UserV3] = (
    (JsPath \ "username").read[String] and
      (JsPath \ "friends").read[Int] and
      (JsPath \ "enemies").read[Int] and
      (JsPath \ "is_alive").read[Boolean]
    ) (UserV3.apply _)
}
/*******************  STEP 4  ********************/
case class UserV4(username: String, friends: Int, enemies: Int, isAlive: Option[Boolean])

object UserV4 {

  import play.api.libs.functional.syntax._
  import play.api.libs.json._

  implicit val userReads: Reads[UserV4] = (
    (JsPath \ "username").read[String] and
      (JsPath \ "friends").read[Int] and
      (JsPath \ "enemies").read[Int] and
      (JsPath \ "is_alive").readNullable[Boolean]
    ) (UserV4.apply _)
}

/*******************  STEP 5  ********************/
case class Response(id: Long, friend_ids: Seq[Long])

object Response {

  val r: Reads[Response] = (
    (__ \ "id").read[Long] and
      (__ \ "friends").read[Seq[Long]](Reads.seq((__ \ "id").read[Long]))
    )(Response.apply _)
}

/*******************  STEP 5  ********************/
case class JsonExampleV1(field: String, date: DateTime)
object JsonExampleV1{
  implicit val r: Reads[JsonExampleV1] = (
    (__ \ "field").read[String] and
      (__ \ "date").read[DateTime](Reads.DefaultJodaDateReads)
    )(JsonExampleV1.apply _)
}

/*******************  STEP 7  ********************/
case class MyIdentifier(id: Long)

case class JsonExampleV2(id: MyIdentifier, data: String)

object JsonExampleV2 {
  implicit val r: Reads[JsonExampleV2] = (
      (__ \ "id").read[Long].map(MyIdentifier) and
    (__ \ "data").read[String]
    )(JsonExampleV2.apply _)
}


object Test {

  val jsonV1 = """{ "field": "example field", "date": 1459014762000 }"""

  val jsonV2 = """ { "id": 91, "data": "String data" }"""
}
