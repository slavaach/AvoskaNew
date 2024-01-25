package ru.yandex.slavaach.nullapplicatoin.features.weather.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    @Expose
    @SerializedName("coord")
    val coord: Coord,
    @Expose
    @SerializedName("weather")
    val weather: List<WeatherClouds>,
    @Expose
    @SerializedName("name")
    val name: String?,
    @Expose
    @SerializedName("main")
    val main: MainWeather?,
    @Expose
    @SerializedName("wind")
    val wind: Wind?,
) : Parcelable

@Parcelize
data class Coord(
    @Expose
    @SerializedName("lon")
    val lon: Double?,
    @Expose
    @SerializedName("lat")
    val lat: Double?,
) : Parcelable

@Parcelize
data class WeatherClouds(
    @Expose
    @SerializedName("id")
    val id: Int?,
    @Expose
    @SerializedName("main")
    val main: String?,
    @Expose
    @SerializedName("description")
    val description: String?,
    @Expose
    @SerializedName("icon")
    val icon: String?,
) : Parcelable

@Parcelize
data class MainWeather(
    @Expose
    @SerializedName("temp")
    val temp: Double?,
) : Parcelable

@Parcelize
data class Wind(
    @Expose
    @SerializedName("speed")
    val speed: Double?,
) : Parcelable

/*{
   "coord": {
      "lon": -0.1257,
      "lat": 51.5085
   },
   "weather": [
      {
         "id": 804,
         "main": "Clouds",
         "description": "overcast clouds",
         "icon": "04d"
      }
   ],
   "base": "stations",
   "main": {
      "temp": 279.37,
      "feels_like": 276.74,
      "temp_min": 278.07,
      "temp_max": 280.1,
      "pressure": 1010,
      "humidity": 74
   },
   "visibility": 10000,
   "wind": {
      "speed": 3.6,
      "deg": 300
   },
   "clouds": {
      "all": 100
   },
   "dt": 1705242207,
   "sys": {
      "type": 2,
      "id": 2075535,
      "country": "GB",
      "sunrise": 1705219240,
      "sunset": 1705249051
   },
   "timezone": 0,
   "id": 2643743,
   "name": "London",
   "cod": 200
}*/
