package com.gc.learning.scala.scalajs.app.webapp

import org.scalajs.dom.html
import scalatags.JsDom.all._

import scala.scalajs.js.annotation.JSExport

/**
  * Created by ganeshchand on 2/26/17.
  */
@JSExport
object WeatherSearch{
  @JSExport
  def main(target: html.Div): Unit = {
    val myFavoriteCities = List("San Francisco", "Kathmandu", "Bangalore", "Tokyo", "New York")
    target.appendChild(
      div (
        h1("Weather Search"),
        h3("My favorite cities:"),
          myFavoriteCities.map {city => li(city)}
      ).render
    )

  }
}
