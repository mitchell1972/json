package JsonRun

import com.pedrorijo91.json._
import play.api.libs.json.Json


trait helper{


  lazy val first = Json.parse(Test.jsonV2).as[JsonExampleV2]

  lazy val second = Json.parse(Test.jsonV3).as[UserV3]

  lazy val third = Json.parse(Test.jsonV1).as[UserV1]

  lazy val fourth = Json.parse(Test.jsonV4).as[myFirstJson]

  //def summUp(s:Seq[Int]):Int={}

}
