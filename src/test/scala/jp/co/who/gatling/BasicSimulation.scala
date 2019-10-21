package jp.co.who.gatling

import com.fasterxml.jackson.databind.JsonNode
import io.gatling.core.Predef._
import io.gatling.core.check.{CheckBuilder, SaveAs}
import io.gatling.core.check.jsonpath.{JsonFilter, JsonPathCheckType}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class BasicSimulation extends Simulation {

  val httpConf: HttpProtocolBuilder = http
    .baseUrl("http://localhost:9000")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val scn: ScenarioBuilder = scenario("BasicSimulation")
    .exec(http("request_1")
      .get("/")
    .check(status.is(200)))
    .pause(5)

  val scn2: ScenarioBuilder = scenario("BasicSimulation2")
    .exec(http("request_2")
      .get("/rawJson")
      .check(
        status.is(200),
        getJson("$..part1.part2", true),
        getJson("$..part1.str", "sample")
      )
    )
    .pause(5)

  def getJson[A: JsonFilter](path: String, value: A)
  : CheckBuilder[JsonPathCheckType, JsonNode, A]
    with SaveAs[JsonPathCheckType, JsonNode, A] =
    jsonPath(path).ofType[A].is(value)

  private val h = Seq(scn, scn2)
    .map(_.inject(atOnceUsers(1)))
    .toList
  println(h)

  setUp(
    h
  ).protocols(httpConf)
}