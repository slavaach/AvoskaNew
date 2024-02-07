package ru.yandex.slavaach.nullapplicatoin.features.weather

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.launch
import ru.yandex.slavaach.nullapplicatoin.ActivityContextHolder
import ru.yandex.slavaach.nullapplicatoin.R
import ru.yandex.slavaach.nullapplicatoin.component.bottomBar.TypeBottomBar
import ru.yandex.slavaach.nullapplicatoin.component.list.comp.WeatherListConponent
import ru.yandex.slavaach.nullapplicatoin.component.topBar.ClickOnTitle
import ru.yandex.slavaach.nullapplicatoin.component.topBar.DefaltTopAppBarUseCase
import ru.yandex.slavaach.nullapplicatoin.core.domain.ErrorEntity
import ru.yandex.slavaach.nullapplicatoin.core.presentation.base.BaseViewModel
import ru.yandex.slavaach.nullapplicatoin.core.presentation.event.SetClickOnTitle
import ru.yandex.slavaach.nullapplicatoin.core.presentation.event.SetIconTitle
import ru.yandex.slavaach.nullapplicatoin.core.presentation.event.SetSubTitleName
import ru.yandex.slavaach.nullapplicatoin.core.presentation.event.TitleName
import ru.yandex.slavaach.nullapplicatoin.features.weather.data.Weather
import timber.log.Timber

class WeatherViewModel(
    val weatherUseCase: WeatherUseCase,
    val activityContextHolder: ActivityContextHolder,
    val defaltTopAppBarUseCase: DefaltTopAppBarUseCase,
) : BaseViewModel() {

    val state = mutableStateOf(
        State(
            bottomBar = TypeBottomBar.NULL,
            weather = null
        )
    )

    init {
        super.onInit()
        viewModelScope.launch {
            runCatching {
                weatherUseCase.getWeather()
            }.onSuccess {
                state.value = state.value.copy(
                    weather = it,
                )
                val temp = if (state.value.weather?.main?.temp != null) {
                    activityContextHolder.contextRef?.get()?.getString(R.string.temper_title) + " " +
                        (state.value.weather?.main?.temp!!.minus(273.15).toInt()) + " " +
                        activityContextHolder.contextRef?.get()?.getString(R.string.temper_post)
                } else {
                    activityContextHolder.contextRef?.get()?.getString(R.string.temper_null)
                }

                val wind = if (state.value.weather?.wind?.speed != null) {
                    activityContextHolder.contextRef?.get()?.getString(R.string.wind_title) + " " +
                        (state.value.weather?.wind?.speed) + " " +
                        activityContextHolder.contextRef?.get()?.getString(R.string.wind_post)
                } else {
                    activityContextHolder.contextRef?.get()?.getString(R.string.wind_null)
                }
                _state.value = _state.value.copy(
                    list = listOf(
                        WeatherListConponent(it.name ?: ""),
                        WeatherListConponent(it.weather.firstOrNull()?.description ?: ""),
                        WeatherListConponent(temp ?: ""),
                        WeatherListConponent(wind ?: ""),
                    )
                )
            }.onFailure {
                Timber.tag("7777").e("error $it")
                callError(ErrorEntity.ErrorWithAlertString(it.message ?: "Даже не знаю что сказать"))
            }
        }
    }
    fun setTitleName(name: String) {
        viewModelScope.launch {
            defaltTopAppBarUseCase.emitEventTitleNameSource(TitleName.SetTitleName(name))
        }
    }

    fun setIconTitle(icon: Int) {
        viewModelScope.launch {
            defaltTopAppBarUseCase.emitEventIconTitleSource(SetIconTitle(icon))
        }
    }

    fun setSubTitleName(name: String) {
        viewModelScope.launch {
            defaltTopAppBarUseCase.emitEventSubTitleNameSource(SetSubTitleName(name))
        }
    }

    fun setClickOnTitle(it: ClickOnTitle) {
        viewModelScope.launch {
            defaltTopAppBarUseCase.emitEventClickOnTitleSource(SetClickOnTitle(it))
        }
    }

    data class State(
        val bottomBar: TypeBottomBar,
        val weather: Weather?,
    )
}
