package ru.yandex.slavaach.nullapplicatoin.features.weather

import ru.yandex.slavaach.nullapplicatoin.ActivityContextHolder
import ru.yandex.slavaach.nullapplicatoin.R
import ru.yandex.slavaach.nullapplicatoin.features.weather.data.Weather
import ru.yandex.slavaach.nullapplicatoin.features.weather.net.WeatherRepository

class WeatherUseCase(
    private val weatherRepository: WeatherRepository,
    private val activityContextHolder: ActivityContextHolder,
) {
    suspend fun getWeather(): Weather {
        val key = activityContextHolder.contextRef?.get()?.getString(R.string.weather_key) ?: ""
        return weatherRepository.getLatLonData(lat = "55.645382", lon = "37.510254", key = key)
    }
}
