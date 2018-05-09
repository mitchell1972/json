package com.pedrorijo91.json

import java.util.Calendar

import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.time.{Minute, Span}
import org.scalatest.{Matchers, WordSpec}
import play.api.libs.ws.WSResponse

import scala.concurrent.Future

class ChangeVATContractTests extends WordSpec with Matchers with ScalaFutures with IntegrationPatience with JsonStructuralSpec {

  import Requests._

  val desUrl: String = "" //TODO: Point this to the actual url.
  final val timeOut: Timeout = timeout(Span(1, Minute))

  def post(jsonFile: String, ackRef: String = newAckRef()): Future[WSResponse] = {
    println(s"Ack Ref for $jsonFile is $ackRef. Submitted to DES at ${Calendar.getInstance().getTime}")
    buildRequest(desUrl).post(
      buildRequestBody(jsonFile, ackRef)
    )
  }


  "The DES CT Registration end point" should {
    "accept a submission" when {
      "filling in all optional fields" in {
        whenReady(post("TC01.json"), timeOut) {
          response =>
            response.status shouldBe 202
            response.json should haveStringJsonFields(
              "processingDate",
              "acknowledgementReference"
            )
        }
      }
    }
  }
}