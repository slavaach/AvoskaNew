package ru.yandex.slavaach.nullapplicatoin.features.buy

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val buyModule = module {

    viewModel {
        BuyViewModel(get(), get(), get(), get(), get(),)
    }
}
