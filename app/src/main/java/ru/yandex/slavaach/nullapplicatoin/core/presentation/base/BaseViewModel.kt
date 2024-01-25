package ru.yandex.slavaach.nullapplicatoin.core.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.yandex.slavaach.nullapplicatoin.component.list.TypeListComponent
import ru.yandex.slavaach.nullapplicatoin.core.domain.ErrorEntity
import ru.yandex.slavaach.nullapplicatoin.core.presentation.ErrorResolver
import timber.log.Timber

abstract class BaseViewModel : ViewModel(), KoinComponent {

    private val viewModelJob = SupervisorJob()

    protected val errorResolver: ErrorResolver by inject()

    open fun onClick(data: TypeOnClick): Any? {
        return null
    }

    val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun callError(e: ErrorEntity) {
        errorResolver.callError(e)
    }

    open fun onInit() {
        Timber.tag("7777").v("${javaClass.simpleName} onInit")
    }

    data class StateList(val list: List<TypeListComponent>)

    val _state = MutableStateFlow(
        StateList(listOf())
    )

    val stateOld: StateFlow<StateList> = _state.asStateFlow()

    override fun onCleared() {
        Timber.tag("7777").v("%s onCleared", javaClass.simpleName)
        super.onCleared()
        viewModelJob.cancelChildren()
    }
}
