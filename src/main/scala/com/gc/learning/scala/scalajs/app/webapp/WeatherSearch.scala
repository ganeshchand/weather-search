package com.gc.learning.scala.scalajs.app.webapp


import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom.html
import org.scalajs.dom
import dom.ext._

import scalatags.JsDom.all._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.util.{Failure, Success}


/**
  * Created by ganeshchand on 2/26/17.
  */
@JSExport
object WeatherSearch {
  @JSExport
  def main(target: html.Div): Unit = {

    val baseURL = s"http://api.openweathermap.org/data/2.5/weather"
    val apiKey = "d17761b362da36469f0340da7cb88f8d"
    val searchCity = "Kathmandu"
    val url = s"$baseURL?q=$searchCity&&APPID=$apiKey"

    val headElement = h1("Weather Search").render
    target.appendChild(headElement)

    val f = Ajax.get(url)

    f.onComplete {
      case Success(xhr) =>
        val json = js.JSON.parse(xhr.responseText)
        val weather = json.weather.pop().main.toString
        //        dom.console.log(json)
        //        dom.console.log(json.main.temp.toString)
        val city = json.name.toString
        val countryCode = json.sys.country.toString
        val countryName = CountryCode.getCountryName(countryCode).getOrElse("Unknown")

        def celcius(kelvins: js.Dynamic) = {
          kelvins.asInstanceOf[Double] - 273.15 // T(°C) = T(K) - 273.15
        }.toInt

        val min = celcius(json.main.temp_min)
        val max = celcius(json.main.temp_max)
        val humidity = json.main.humidity.toString
        target.appendChild(
          div(
            b(s"Weather in $city:"),
            ul(
              li(b("Country: "), countryName),
              li(b("Weather: "), weather),
              li(b("Temp(Celcius): "), s"$min°C - $max°C"),
              li(b("Humidity: "), humidity)
            )
          ).render
        )
      case Failure(e: org.scalajs.dom.ext.AjaxException) => target.appendChild(p(s"Couldn't connect to $baseURL").render)
        dom.console.log(s"${e.toString}")
      case Failure(e) => target.appendChild(p(s"failed: ${e.toString}").render)
    }
  }
}
