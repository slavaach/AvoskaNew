package ru.yandex.slavaach.nullapplicatoin.features.reference

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.yandex.slavaach.nullapplicatoin.ActivityContextHolder
import ru.yandex.slavaach.nullapplicatoin.R
import ru.yandex.slavaach.nullapplicatoin.component.bottomBar.OnClickAddDefaltBottomBar
import ru.yandex.slavaach.nullapplicatoin.component.bottomBar.OnClickDeleteDefaltBottomBar
import ru.yandex.slavaach.nullapplicatoin.component.bottomBar.OnClickUpdateDefaltBottomBar
import ru.yandex.slavaach.nullapplicatoin.component.bottomBar.TypeBottomBar
import ru.yandex.slavaach.nullapplicatoin.component.list.TypeListComponent
import ru.yandex.slavaach.nullapplicatoin.component.list.comp.ReferenceListComponent
import ru.yandex.slavaach.nullapplicatoin.component.topBar.ClickOnTitle
import ru.yandex.slavaach.nullapplicatoin.component.topBar.DefaltTopAppBarUseCase
import ru.yandex.slavaach.nullapplicatoin.core.presentation.Alert
import ru.yandex.slavaach.nullapplicatoin.core.presentation.AlertManager
import ru.yandex.slavaach.nullapplicatoin.core.presentation.base.BaseViewModel
import ru.yandex.slavaach.nullapplicatoin.core.presentation.base.TypeOnClick
import ru.yandex.slavaach.nullapplicatoin.core.presentation.event.SetClickOnTitle
import ru.yandex.slavaach.nullapplicatoin.core.presentation.event.SetIconTitle
import ru.yandex.slavaach.nullapplicatoin.core.presentation.event.SetSubTitleName
import ru.yandex.slavaach.nullapplicatoin.core.presentation.event.TitleName
import ru.yandex.slavaach.nullapplicatoin.core.presentation.event.Transfer
import ru.yandex.slavaach.nullapplicatoin.core.presentation.event.TransferMemorySource
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.ReferenceUseCase
import timber.log.Timber

class ReferenceViewModel(
    var referenceUseCase: ReferenceUseCase,
    val alertManager: AlertManager,
    val activityContextHolder: ActivityContextHolder,
    val transferMemorySource: TransferMemorySource,
    val defaltTopAppBarUseCase: DefaltTopAppBarUseCase,
) : BaseViewModel() {
    val state = mutableStateOf(
        State(
            bottomBar = TypeBottomBar.DEFAULT,
            openAddDialog = false,
            nameForAddOrUpdate = "",
            sortForAddOrUpdate = 0,
            titleDialog = "",
        )
    )

    var openAddOrUpdate = true // значит открыт диалог добавления

    data class State(
        val bottomBar: TypeBottomBar,
        val openAddDialog: Boolean,
        val nameForAddOrUpdate: String,
        val sortForAddOrUpdate: Int,
        val titleDialog: String,
    )

    var selectedId = -1L

    init {
        super.onInit()
        updateList()
    }

    fun setTitleName(name: String) {
        viewModelScope.launch {
            defaltTopAppBarUseCase.emitEventTitleNameSource(TitleName.SetTitleName(name))
        }
    }
    fun setSubTitleName(name: String) {
        viewModelScope.launch {
            defaltTopAppBarUseCase.emitEventSubTitleNameSource(SetSubTitleName(name))
        }
    }

    fun setIconTitle(icon: Int) {
        viewModelScope.launch {
            defaltTopAppBarUseCase.emitEventIconTitleSource(SetIconTitle(icon))
        }
    }

    fun setClickOnTitle(it: ClickOnTitle) {
        viewModelScope.launch {
            defaltTopAppBarUseCase.emitEventClickOnTitleSource(SetClickOnTitle(it))
        }
    }
    fun updateList() {
        viewModelScope.launch {
            _state.value =
                _state.value.copy(list = referenceUseCase.selectHome().map { ReferenceListComponent(it.id, it.name, it.order, it.id == selectedId) })
        }
    }

    override fun onClick(data: TypeOnClick): Any? {
        when (data) {
            is OnClickAddDefaltBottomBar -> {
                return addDialog()
            }

            is OnClickDeleteDefaltBottomBar -> {
                return delete()
            }

            is OnClickUpdateDefaltBottomBar -> {
                return updateDialog()
            }

            else -> return null
        }
    }

    fun addDialog() {
        val title = activityContextHolder.contextRef?.get()?.getString(R.string.add_reference) ?: ""
        openAddOrUpdate = true
        state.value = state.value.copy(
            openAddDialog = true,
            titleDialog = title,
            nameForAddOrUpdate = "",
            sortForAddOrUpdate = -1
        )
    }

    fun updateDialog() {
        val delItem = _state.value.list.firstOrNull { (it as? ReferenceListComponent)?.isActiv ?: false }
        if (delItem == null) return
        if (delItem !is ReferenceListComponent) return
        openAddOrUpdate = false
        val title = activityContextHolder.contextRef?.get()?.getString(R.string.update_reference) ?: ""
        state.value = state.value.copy(
            openAddDialog = true,
            titleDialog = title,
            nameForAddOrUpdate = delItem.name,
            sortForAddOrUpdate = delItem.order
        )
    }

    fun add() {
        viewModelScope.launch {
            try {
                val idForUpdate = if (openAddOrUpdate) {
                    0
                } else {
                    (_state.value.list.firstOrNull { (it as? ReferenceListComponent)?.isActiv ?: false } as? ReferenceListComponent)?.id ?: 0
                }
                referenceUseCase.addHome(idForUpdate, state.value.nameForAddOrUpdate, state.value.sortForAddOrUpdate)
                state.value = state.value.copy(nameForAddOrUpdate = "", sortForAddOrUpdate = -1)

                updateList()

                transferMemorySource.emitEvent(Transfer.UpdateHome())
            } catch (e: Exception) {
                Timber.tag("7777").e("error $e")
            }
        }
    }

    fun adding() {
        state.value = state.value.copy(openAddDialog = false)
    }

    fun setNameForAddOrUpdate(name: String) {
        state.value = state.value.copy(nameForAddOrUpdate = name)
    }

    fun setSortForAddOrUpdate(sort: Int) {
        state.value = state.value.copy(sortForAddOrUpdate = sort)
    }

    fun setSelected(item: TypeListComponent) {
        if (item !is ReferenceListComponent) return
        val lstRef = _state.value.list.filter { it is ReferenceListComponent }.map { it as ReferenceListComponent }
        val isNotSelect = lstRef.firstOrNull { it.id == item.id && it.isActiv } == null
        if (isNotSelect) {
            selectedId = item.id
            _state.value = _state.value.copy(
                list = _state.value.list.map {
                    if (it is ReferenceListComponent) {
                        ReferenceListComponent(it.id, it.name, it.order, (it.id == item.id))
                    } else {
                        it
                    }
                }
            )
        } else {
            selectedId = -1L
            _state.value = _state.value.copy(
                list = _state.value.list.map {
                    if (it is ReferenceListComponent) {
                        ReferenceListComponent(it.id, it.name, it.order, false)
                    } else {
                        it
                    }
                }
            )
        }
    }

    fun delete() {
        val delItem = _state.value.list.firstOrNull { (it as? ReferenceListComponent)?.isActiv ?: false }
        if (delItem == null) return
        if (delItem !is ReferenceListComponent) return
        alertManager.showAlert(
            Alert.Info(
                title = R.string.delete_item_title,
                text = "Удаляем ${delItem.name}",
                callbackOk = {
                    viewModelScope.launch {
                        try {
                            referenceUseCase.deletHome(delItem.id)
                            updateList()
                        } catch (e: Exception) {
                            Timber.tag("7777").e("error $e")
                        }
                    }
                },
                callbackNot = null,
                textOkButton = R.string.delete_item,
                textNotButton = R.string.cancel_item,
            )
        )
    }
}
