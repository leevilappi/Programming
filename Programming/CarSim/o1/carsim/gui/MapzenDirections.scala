


////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it's not necessary
// that you understand or even look at the code in this file.
//////////////////////////////////////////////////////////////

package o1.carsim.gui

import scala.util._
import rapture.net._
import rapture.json._
import rapture.io._
import rapture.json.jsonParsers.jackson._
import rapture.core.strategy.throwExceptions
import rapture.core.timeSystems.javaUtil

/**
 * The singleton object `MapzenDirections` provides an interface to the Mapzen routing service over the internet.
 *
 * '''NOTE TO STUDENTS: In this course, you don't need to understand how this object works or can be used.'''
 *
 * (This class is not really a part of the GUI ''per se''. It is here as an artifact of the programming assigment.)
 */
object MapzenDirections { // see: https://mapzen.com/documentation/mobility/turn-by-turn/api-reference/

  def findRoute(origin: String, destination: String) = {
    
    def scrapeRouteSegments(directionsJson: Json) = {
      val status = attempt(directionsJson.trip.status.as[Int], "Received an invalid response from Mapzen: no status code in JSON.")
      status match {
        case 0           => attempt(directionsJson.trip.legs(0).as[Step].segments, "Received an invalid JSON response from Mapzen: failed to get the route.")
        case otherStatus => error("Could not determine the directions. Status message from the Mapzen service: " + directionsJson.message.as[String] + "(status code " + otherStatus + ")")
      }
    }
    val originCoords      = parseCoords(origin)      getOrElse error("Invalid origin of travel: " + origin)
    val destinationCoords = parseCoords(destination) getOrElse coordsOfNamedPlace(destination, maybeNear=originCoords)
    val jsonParamsForDirectionsAPI = s"""{"locations":[ ${originCoords.toJson}, ${destinationCoords.toJson}], "costing":"auto", "narrative":"false" }"""
    val searchParameters = Map('json -> jsonParamsForDirectionsAPI, 'api_key -> APIKeys.Directions)
    val searchURL        = Http / "valhalla.mapzen.com" / "route" /? searchParameters
    val httpResponse     = attempt(searchURL.get(5000L),     "Failed to access Mapzen directions search. Please make sure your network connection is working.")
    val responseBody     = attempt(httpResponse.slurp[Char], "Failed to access Mapzen directions search. Please make sure your network connection is working.")
    if (httpResponse.status != HttpOK) error("No route found. Message from the Mapzen service: " + responseBody) 
    val directionsJson   = attempt(Json.parse(responseBody), "Received an invalid JSON response from the Mapzen directions service.")
    scrapeRouteSegments(directionsJson)
  }

  case class Coords(val lat: Double, val lng: Double) {
    def toJson = s"""{ "lat":$lat, "lon":$lng }"""   
  }

  case class Segment(val distance: Double, val origin: Coords, val destination: Coords)

  case class Step(val shape: String, val summary: Json, val maneuvers: Json) {

    lazy val segments = {
      def distanceSortOf(a: Coords, b: Coords) = math.hypot(b.lat - a.lat, b.lng - a.lng)
      val points         = decodePolyline(shape.iterator)
      val lengthsAndEnds = for ((from, to) <- points zip points.tail) yield (distanceSortOf(from, to), from, to)
      val totalLength    = lengthsAndEnds.foldLeft(0.0)(_ + _._1)
      for ((dist, from, to) <- lengthsAndEnds) yield Segment(dist / totalLength * this.summary.length.as[Double] * 1000, from, to) // the length in meters for each polyline segment is not provided, so we use this simple metric that is good enough for current purposes
    }

    private def decodePolyline(encodedPolyline: Iterator[Char]) = {

      val offsets = new Iterator[(Int, Int)] {
        def hasNext = encodedPolyline.hasNext
        def next() = (nextOffset(), nextOffset())
        private def nextOffset() = {
          var shift = 0
          var chunks = 0
          var continueBitPlusChunk = 0
          do {
            continueBitPlusChunk = encodedPolyline.next() - 63
            val chunk = (continueBitPlusChunk & 0x1F)
            chunks |= (chunk << shift)
            shift += 5
          } while (continueBitPlusChunk > 0x1F)
          val offsetPossiblyInverted = chunks >> 1
          if ((chunks & 1) == 0) offsetPossiblyInverted else ~offsetPossiblyInverted
        }
      }

      if (offsets.hasNext) {
        def addOffset(base: (Int, Int), offset: (Int, Int)) = (base, offset) match { case ((lat, lng), (latOffset, lngOffset)) => (lat + latOffset, lng + lngOffset) }
        val coordPairs = offsets.scanLeft(offsets.next())(addOffset)
        val coords = for ((lat, lng) <- coordPairs) yield Coords(lat.toDouble / 1000000, lng.toDouble / 1000000)
        coords.toIndexedSeq
      } else {
        IndexedSeq.empty
      }
    }

  }

  def coordsOfNamedPlace(placeName: String, maybeNear: Coords) = {
    val searchParameters   = Map('text -> placeName, 'api_key -> APIKeys.Coordinates, 'size -> "1", Symbol("focus.point.lat") -> maybeNear.lat.toString, Symbol("focus.point.lon") -> maybeNear.lng.toString)
    val searchURL          = Http / "search.mapzen.com" / "v1" / "search" /? searchParameters
    val httpResponse       = attempt(searchURL.get(5000L),     "Failed to access Mapzen coordinate search. Please make sure your network connection is working.")
    val responseBody       = attempt(httpResponse.slurp[Char], "Failed to access Mapzen coordinate search. Please make sure your network connection is working.")
    if (httpResponse.status != HttpOK) error(s"Could not determine the coordinates of '$placeName'. Message from the Mapzen service: $responseBody") 
    val locationJson       = attempt(Json.parse(responseBody),                                        "Received an invalid JSON response from Mapzen coordinate search.")
    val coords             = attempt(locationJson.features(0).geometry.coordinates.as[Array[Double]], s"No coordinates available for '$placeName'.")
    Coords(coords(1), coords(0))
  }

  
  
  private def error(message: String) = throw new CarMap.DirectionsException(message)
  private def attempt[Result](action: =>Result, message: String) = Try(action) getOrElse error(message)
  
  private object APIKeys { // Please do not use these API keys in other projects. You can create your own for free at Mapzen if you want.
    val Directions  = "valhalla-MEJJQ6U"    
    val Coordinates = "search-fqXy3RU"
  }
  private val HttpOK = 200
  private val LatCommaLong = {
    val Comma = """\s*,\s*"""
    val Coord = """((?:-)?\d+(?:\.\d+)?)"""
    (Coord + Comma + Coord).r
  }
  private def parseCoords(coordsString: String) = coordsString match {
    case LatCommaLong(lat, lng) => Try(Coords(lat.toDouble, lng.toDouble)).toOption 
    case _                      => None
  }
    
}


