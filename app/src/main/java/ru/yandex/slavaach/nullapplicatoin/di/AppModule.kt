package ru.yandex.slavaach.nullapplicatoin.di

import com.google.gson.GsonBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import ru.yandex.slavaach.nullapplicatoin.ActivityContextHolder
import ru.yandex.slavaach.nullapplicatoin.MainViewModel
import ru.yandex.slavaach.nullapplicatoin.MainViewModelHolder
import ru.yandex.slavaach.nullapplicatoin.core.data.gson.LocalDateJsonDeserializer
import ru.yandex.slavaach.nullapplicatoin.core.data.gson.LocalDateTimeTypeAdapter
import ru.yandex.slavaach.nullapplicatoin.core.presentation.AlertManager
import ru.yandex.slavaach.nullapplicatoin.core.presentation.ErrorResolver
import ru.yandex.slavaach.nullapplicatoin.features.custom.data.SettingUseCase
import ru.yandex.slavaach.nullapplicatoin.navigation.HomeNav

val appModule = module {
    single {
        GsonBuilder()
            .setLenient()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter())
            .registerTypeAdapter(LocalDate::class.java, LocalDateJsonDeserializer())
            .create()
    }
    single { AlertManager(get()) }
    single { ErrorResolver(get(),) }

    single { ActivityContextHolder() }

    single { SettingUseCase(get()) }

    single { MainViewModelHolder() }
   /* single<SvgImageTransactionRepository> {
        if (BuildConfig.USE_FAKE_SOURCE) {
            SvgImageRepositoryTransactionFake(get(), get(), get(), get(), get())
        } else {
            SvgImageRepositoryTransactionImpl(get(), get(), get())
        }
    }*/

    viewModel { MainViewModel(get(), get(), get(), get(), get(),) }
    single { HomeNav(get(), get(), get(),) }
}
