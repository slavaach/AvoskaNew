package ru.yandex.slavaach.nullapplicatoin.features.weather.net

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.yandex.slavaach.nullapplicatoin.features.weather.data.Weather

interface WeatherApi {
    @GET("/data/2.5/weather")
    suspend fun getLatLonData(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("lang") lang: String = "ru",
        @Query("APPID") key: String?
    ): Weather

    @GET("/data/2.5/find")
    fun getCityData(
        @Query("q") city: String?,
        @Query("lang") lang: String = "ru",
        @Query("APPID") key: String?
    ): Call<ResponseBody>
}
