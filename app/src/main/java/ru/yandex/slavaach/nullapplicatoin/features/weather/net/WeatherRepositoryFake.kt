package ru.yandex.slavaach.nullapplicatoin.features.weather.net

import ru.yandex.slavaach.nullapplicatoin.features.weather.data.Weather

class WeatherRepositoryFake : WeatherRepository {
    override suspend fun getLatLonData(lat: String?, lon: String?, lang: String, key: String?): Weather {
        TODO("Not yet implemented")
    }
}
