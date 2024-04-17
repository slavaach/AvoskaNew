package ru.yandex.slavaach.nullapplicatoin.features.custom

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.yandex.slavaach.nullapplicatoin.ActivityContextHolder
import ru.yandex.slavaach.nullapplicatoin.R
import ru.yandex.slavaach.nullapplicatoin.component.bottomBar.OnClickAddDefaltBottomBar
import ru.yandex.slavaach.nullapplicatoin.component.bottomBar.OnClickDeleteDefaltBottomBar
import ru.yandex.slavaach.nullapplicatoin.component.bottomBar.OnClickUpdateDefaltBottomBar
import ru.yandex.slavaach.nullapplicatoin.component.bottomBar.TypeBottomBar
import ru.yandex.slavaach.nullapplicatoin.component.list.TypeListComponent
import ru.yandex.slavaach.nullapplicatoin.component.list.comp.CustomListComponent
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
import ru.yandex.slavaach.nullapplicatoin.features.custom.data.Custom
import ru.yandex.slavaach.nullapplicatoin.features.custom.data.CustomUseCase
import ru.yandex.slavaach.nullapplicatoin.features.custom.data.SettingUseCase
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.sale.Sale
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.sale.SaleUseCase
import timber.log.Timber

class CustomViewModel(
    var customUseCase: CustomUseCase,
    val settingUseCase: SettingUseCase,
    var saleUseCase: SaleUseCase,
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
            idSale = 0,
            nameSale = ""
        )
    )

    val _sale = MutableStateFlow(
        SaleList(listOf())
    )

    val stateSale: StateFlow<SaleList> = _sale.asStateFlow()

    var openAddOrUpdate = true // значит открыт диалог добавления
    var idHome = -1L

    data class State(
        val bottomBar: TypeBottomBar,
        val openAddDialog: Boolean,
        val nameForAddOrUpdate: String,
        val sortForAddOrUpdate: Int,
        val titleDialog: String,
        val idSale: Long,
        val nameSale: String,
    )

    var selectedId = -1L

    init {
        super.onInit()
        updateList()
        updateSale()
        viewModelScope.launch {
            transferMemorySource.observeTransferEvent().collect {
                if (it is Transfer.UpdateHomeSelectCustom) updateList(it.id)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
    fun setTitleName() {
        viewModelScope.launch {
            defaltTopAppBarUseCase.emitEventTitleNameSource(TitleName.SetTitleNameHome())
        }
    }

    fun setClickOnTitle(it: ClickOnTitle) {
        viewModelScope.launch {
            defaltTopAppBarUseCase.emitEventClickOnTitleSource(SetClickOnTitle(it))
        }
    }

    fun updateList() {
        viewModelScope.launch {
            settingUseCase.select()?.let { sh ->
                _state.value =
                    _state.value.copy(
                        list = customUseCase.select(sh.idHome).map {
                            CustomListComponent(
                                id = it.id,
                                name = it.name,
                                order = it.sale.toInt(),
                                saleName = it.salename ?: "",
                                isActiv = it.id == selectedId
                            )
                        }
                    )
            }
        }
    }

    fun updateList(id: Long) {
        idHome = id
        viewModelScope.launch {
            _state.value =
                _state.value.copy(
                    list = customUseCase.select(id).map {
                        CustomListComponent(
                            it.id,
                            it.name,
                            it.sale.toInt(),
                            it.salename ?: "",
                            it.id == selectedId
                        )
                    }
                )
        }
    }

    fun setSubTitleName(name: String) {
        viewModelScope.launch {
            defaltTopAppBarUseCase.emitEventSubTitleNameSource(SetSubTitleName(name))
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
            sortForAddOrUpdate = -1,
            idSale = 0,
            nameSale = "",
        )
    }

    fun updateDialog() {
        val delItem = _state.value.list.firstOrNull { (it as? CustomListComponent)?.isActiv ?: false }
        if (delItem == null) return
        if (delItem !is CustomListComponent) return
        val nameSale = _sale.value.list.firstOrNull { it.id == delItem.order.toLong() }
        openAddOrUpdate = false
        val title = activityContextHolder.contextRef?.get()?.getString(R.string.update_reference) ?: ""
        state.value = state.value.copy(
            openAddDialog = true,
            titleDialog = title,
            nameForAddOrUpdate = delItem.name,
            sortForAddOrUpdate = delItem.order,
            idSale = delItem.order.toLong(),
            nameSale = nameSale?.name ?: "",
        )
    }

    fun add() {
        if (idHome == null) return
        viewModelScope.launch {
            try {
                val idForUpdate = if (openAddOrUpdate) {
                    0
                } else {
                    (_state.value.list.firstOrNull { (it as? CustomListComponent)?.isActiv ?: false } as? CustomListComponent)?.id ?: 0
                }
                val cs = Custom(
                    id = idForUpdate,
                    name = state.value.nameForAddOrUpdate,
                    home = idHome,
                    sale = state.value.idSale,
                )
                customUseCase.add(cs)
                state.value = state.value.copy(nameForAddOrUpdate = "", sortForAddOrUpdate = -1)

                updateList()
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

    fun setIconTitle(icon: Int) {
        viewModelScope.launch {
            defaltTopAppBarUseCase.emitEventIconTitleSource(SetIconTitle(icon))
        }
    }

    fun setSelected(item: TypeListComponent) {
        if (item !is CustomListComponent) return
        val lstRef = _state.value.list.filter { it is CustomListComponent }.map { it as CustomListComponent }
        val isNotSelect = lstRef.firstOrNull { it.id == item.id && it.isActiv } == null
        if (isNotSelect) {
            selectedId = item.id
            _state.value = _state.value.copy(
                list = _state.value.list.map {
                    if (it is CustomListComponent) {
                        CustomListComponent(it.id, it.name, it.order, it.saleName, (it.id == item.id))
                    } else {
                        it
                    }
                }
            )
        } else {
            selectedId = -1L
            _state.value = _state.value.copy(
                list = _state.value.list.map {
                    if (it is CustomListComponent) {
                        CustomListComponent(it.id, it.name, it.order, it.saleName, false)
                    } else {
                        it
                    }
                }
            )
        }
    }

    fun delete() {
        val delItem = _state.value.list.firstOrNull { (it as? CustomListComponent)?.isActiv ?: false }
        if (delItem == null) return
        if (delItem !is CustomListComponent) return
        alertManager.showAlert(
            Alert.Info(
                title = R.string.delete_item_title,
                text = "Удаляем ${delItem.name}",
                callbackOk = {
                    viewModelScope.launch {
                        try {
                            customUseCase.delete(delItem.id)
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

    data class SaleList(val list: List<Sale>)


    fun updateSale() {
        viewModelScope.launch {
            _sale.value =
                _sale.value.copy(list = saleUseCase.selectHome().map { Sale(it.id, it.name, it.order) })
        }
    }

    fun setSale(id: Long, name: String) {
        state.value = state.value.copy(
            idSale = id,
            nameSale = name,
        )
    }
}
