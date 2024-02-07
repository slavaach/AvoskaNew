package ru.yandex.slavaach.nullapplicatoin.features.custom

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.yandex.slavaach.nullapplicatoin.features.custom.data.CustomUseCase

val customModule = module {

    viewModel {
        CustomViewModel(get(), get(), get(), get(), get(), get(), get(),)
    }

    single { CustomUseCase(get(),) }
}
