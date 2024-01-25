package ru.yandex.slavaach.nullapplicatoin.features.weather

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create
import ru.yandex.slavaach.nullapplicatoin.features.weather.net.WeatherApi
import ru.yandex.slavaach.nullapplicatoin.features.weather.net.WeatherRepository
import ru.yandex.slavaach.nullapplicatoin.features.weather.net.WeatherRepositoryImpl

val weatherModule = module {
    // Data
    single { get<Retrofit>().create<WeatherApi>() }

    viewModel {
        WeatherViewModel(get(), get(), get())
    }

    single { WeatherUseCase(get(), get()) }

    single<WeatherRepository> {
        /*if (BuildConfig.USE_FAKE_SOURCE) {
            WeatherRepositoryFake()
        } else {*/
        WeatherRepositoryImpl(get())
        // }
    }
}
