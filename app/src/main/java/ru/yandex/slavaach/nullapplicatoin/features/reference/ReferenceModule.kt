package ru.yandex.slavaach.nullapplicatoin.features.reference

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.ReferenceUseCase
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.home.HomeUseCase
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.sale.SaleUseCase

val referenceModule = module {
    // Data
    viewModel { (useCase: ReferenceUseCase) ->
        ReferenceViewModel(useCase, get(), get(), get(), get(),)
    }

    single { HomeUseCase(get()) }
    single { SaleUseCase(get()) }
}
