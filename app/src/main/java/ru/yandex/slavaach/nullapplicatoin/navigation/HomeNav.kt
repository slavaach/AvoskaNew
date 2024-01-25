package ru.yandex.slavaach.nullapplicatoin.navigation

import ru.yandex.slavaach.nullapplicatoin.MainViewModelHolder
import ru.yandex.slavaach.nullapplicatoin.features.buy.BuyFrgm
import ru.yandex.slavaach.nullapplicatoin.features.custom.CustomFrgm
import ru.yandex.slavaach.nullapplicatoin.features.reference.ReferenceFrgm
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.home.HomeUseCase
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.sale.SaleUseCase
import ru.yandex.slavaach.nullapplicatoin.features.weather.WeatherFrgm

class HomeNav(
    val mainViewModelHolder: MainViewModelHolder,
    val homeUseCase: HomeUseCase,
    val saleUseCase: SaleUseCase
) {

    fun openHome() {
        mainViewModelHolder.viewModelRef?.get()?.let { vm ->
            vm.navigator?.push(ReferenceFrgm(homeUseCase, "Дома",))
        }
    }

    fun openSale() {
        mainViewModelHolder.viewModelRef?.get()?.let { vm ->
            vm.navigator?.push(ReferenceFrgm(saleUseCase, "Магазины",))
        }
    }

    fun openWeather() {
        mainViewModelHolder.viewModelRef?.get()?.let { vm ->
            vm.navigator?.push(WeatherFrgm())
        }
    }

    fun openCustom() {
        mainViewModelHolder.viewModelRef?.get()?.let { vm ->
            vm.navigator?.replaceAll(CustomFrgm())
        }
    }

    fun openBuy() {
        mainViewModelHolder.viewModelRef?.get()?.let { vm ->
            vm.navigator?.push(BuyFrgm())
        }
    }
}
