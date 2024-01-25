package ru.yandex.slavaach.nullapplicatoin.features.weather.net

import ru.yandex.slavaach.nullapplicatoin.features.weather.data.Weather

class WeatherRepositoryImpl(
    val weatherApi: WeatherApi
) : WeatherRepository {
    override suspend fun getLatLonData(lat: String?, lon: String?, lang: String, key: String?): Weather {
        return weatherApi.getLatLonData(lat, lon, lang, key)
    }
}
