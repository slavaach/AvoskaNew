package ru.yandex.slavaach.nullapplicatoin.features.weather.net

import ru.yandex.slavaach.nullapplicatoin.features.weather.data.Weather

interface WeatherRepository {
    suspend fun getLatLonData(
        lat: String?,
        lon: String?,
        lang: String = "ru",
        key: String?
    ): Weather
}
