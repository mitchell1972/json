package JsonRun

import com.pedrorijo91.json.{UserV3, myFirstJson}
import play.api.libs.json.Json


object runner extends helper {

  val model = myFirstJson(
    firstName = "John",
    secondName = "Jameson",
    staffNumber = 123456,
    startDate = "1998-06-09",
    staffBoss = UserV3 (
      username = "JohnJames",
      friends = 3,
      enemies = 0,
      isAlive = true
      ),
    currentStaff = true
  )

  def randomElement[A](seq: Seq[A]): A = {
    val randomNum = util.Random.nextInt(seq.length)
    seq(randomNum)
  }
//TODO Change stuff
   lazy val fifth = Json.parse(model.toString).as[myFirstJson]
val json = Json.toJson(model)
  def main(args: Array[String]): Unit = {

    println(model)
    println(fourth)
    assert(model.equals(fourth))
    println(json)
    println(randomElement(Seq("Aleka", "Christina", "Tyler", "Molly")))
    println(randomElement(List(1, 3, 5, 7)))
  }


}
