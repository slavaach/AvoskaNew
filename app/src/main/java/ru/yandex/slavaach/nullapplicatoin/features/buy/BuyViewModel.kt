package ru.yandex.slavaach.nullapplicatoin.features.buy

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.launch
import ru.yandex.slavaach.nullapplicatoin.MainViewModel
import ru.yandex.slavaach.nullapplicatoin.MainViewModelHolder
import ru.yandex.slavaach.nullapplicatoin.component.bottomBar.OnClickFilterBuyBottomBar
import ru.yandex.slavaach.nullapplicatoin.component.bottomBar.OnClickOrderBuyBottomBar
import ru.yandex.slavaach.nullapplicatoin.component.bottomBar.OnClickSaveBuyBottomBar
import ru.yandex.slavaach.nullapplicatoin.component.bottomBar.TypeBottomBar
import ru.yandex.slavaach.nullapplicatoin.component.list.TypeListComponent
import ru.yandex.slavaach.nullapplicatoin.component.list.comp.BuyListComponent
import ru.yandex.slavaach.nullapplicatoin.component.topBar.ClickOnTitle
import ru.yandex.slavaach.nullapplicatoin.core.presentation.AlertManager
import ru.yandex.slavaach.nullapplicatoin.core.presentation.base.BaseViewModel
import ru.yandex.slavaach.nullapplicatoin.core.presentation.base.TypeOnClick
import ru.yandex.slavaach.nullapplicatoin.features.custom.data.Custom
import ru.yandex.slavaach.nullapplicatoin.features.custom.data.CustomUseCase
import ru.yandex.slavaach.nullapplicatoin.features.custom.data.SettingUseCase

class BuyViewModel(
    var customUseCase: CustomUseCase,
    val settingUseCase: SettingUseCase,
    val alertManager: AlertManager,
    val mainViewModelHolder: MainViewModelHolder,
) : BaseViewModel() {
    val state = mutableStateOf(
        State(
            bottomBar = TypeBottomBar.BUY,
            openAddDialog = false,
            nameForAddOrUpdate = "",
            titleDialog = "",
            idSale = 0,
            nameSale = "",
            filterBuy = false,
        )
    )

    var typeOrder: TypeOrder = TypeOrder.ID
    data class State(
        val bottomBar: TypeBottomBar,
        val openAddDialog: Boolean,
        val nameForAddOrUpdate: String,
        val titleDialog: String,
        val idSale: Long,
        val nameSale: String,
        val filterBuy: Boolean,
    )

    var selectedId = -1L

    init {
        super.onInit()
        updateList()

        mainViewModelHolder.viewModelRef?.get()?.setUpDateSettingBuy = object : MainViewModel.upDateSettingBuy {
            override fun update(id: List<Long>) {
                updateList(id)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mainViewModelHolder.viewModelRef?.get()?.setUpDateSettingBuy = null
    }

    fun setIconTitle(icon: Int) {
        mainViewModelHolder.viewModelRef?.get()?.setIconTitle(icon)
    }

    fun setTitleName() {
        mainViewModelHolder.viewModelRef?.get()?.let { mvm ->
            mvm.setTitleNameMulti()
        }
    }

    fun setClickOnTitle(it: ClickOnTitle) {
        mainViewModelHolder.viewModelRef?.get()?.setClickOnTitle(it)
    }

    fun updateList() {
        viewModelScope.launch {
            settingUseCase.select()?.let { sh ->
                orderT(
                    customUseCase.select(sh.idBuyHome).map {
                        BuyListComponent(
                            id = it.id,
                            name = it.name,
                            home = it.homename ?: "",
                            saleName = it.salename ?: "",
                            isActiv = it.id == selectedId,
                            idHome = it.home,
                            idSale = it.sale,
                            isBuy = it.isBuy,
                            saleOrder = it.saleOrder,
                        )
                    }.filter { if (state.value.filterBuy) !it.isBuy else true }
                )
            }
        }
    }

    fun updateList(id: List<Long>) {
        viewModelScope.launch {
            val lst = customUseCase.select(id).map {
                BuyListComponent(
                    it.id,
                    it.name,
                    it.homename ?: "",
                    it.salename ?: "",
                    it.id == selectedId,
                    idHome = it.home,
                    idSale = it.sale,
                    isBuy = it.isBuy,
                    saleOrder = it.saleOrder,
                )
            }
            _state.value = _state.value.copy(list = lst)
        }
    }

    fun setSubTitleName(name: String) {
        mainViewModelHolder.viewModelRef?.get()?.setSubTitleName(name)
    }

    override fun onClick(data: TypeOnClick): Any? {
        when (data) {
            is OnClickSaveBuyBottomBar -> {
                return saveBuy()
            }

            is OnClickFilterBuyBottomBar -> {
                return filter()
            }

            is OnClickOrderBuyBottomBar -> {
                return order(data.typeOrder)
            }

            else -> return null
        }
    }

    fun order(mTypeOrder: TypeOrder) {
        typeOrder = mTypeOrder
        orderT(_state.value.list)
    }
    private fun orderT(list: List<TypeListComponent>) {
        when (typeOrder) {
            TypeOrder.ID -> {
                val lst = list.filter { it is BuyListComponent }.sortedBy { (it as? BuyListComponent)?.id ?: 0L }
                _state.value = _state.value.copy(list = lst)
            }

            TypeOrder.SALE_ID_CUSTOM -> {
                val lst = list.filter { it is BuyListComponent }.sortedBy { (it as? BuyListComponent)?.id ?: 0L }
                    .sortedBy { (it as? BuyListComponent)?.saleOrder ?: 0 }
                _state.value = _state.value.copy(list = lst)
            }

            TypeOrder.SALE_NAME -> {
                val lst = list.filter { it is BuyListComponent }.sortedBy { (it as? BuyListComponent)?.name ?: "" }
                    .sortedBy { (it as? BuyListComponent)?.saleOrder ?: 0 }
                _state.value = _state.value.copy(list = lst)
            }
        }
    }

    fun filter() {
        val isFilter = state.value.filterBuy
        state.value = state.value.copy(filterBuy = !isFilter)
        updateList()
    }

    fun saveBuy() {
        viewModelScope.launch {
            customUseCase.deleteBuy()
            updateList()
        }
    }

    fun isBuy(id: Long) {
        val item = _state.value.list.firstOrNull { (it as? BuyListComponent)?.id == id }
        if (item == null && item !is BuyListComponent) return
        viewModelScope.launch {
            val itemData = (item as BuyListComponent)
            val custom = Custom(
                id = id,
                name = itemData.name,
                home = item.idHome,
                sale = item.idSale,
                isBuy = !item.isBuy
            )
            customUseCase.add(custom)
            updateList()
        }
    }

    fun setSelected(item: TypeListComponent) {
        if (item !is BuyListComponent) return
        val lstRef = _state.value.list.filter { it is BuyListComponent }.map { it as BuyListComponent }
        val isNotSelect = lstRef.firstOrNull { it.id == item.id && it.isActiv } == null
        if (isNotSelect) {
            selectedId = item.id
            _state.value = _state.value.copy(
                list = _state.value.list.map {
                    if (it is BuyListComponent) {
                        BuyListComponent(
                            it.id,
                            it.name,
                            it.home,
                            it.saleName,
                            if (it.id == item.id) true else it.isActiv,
                            idHome = it.idHome,
                            idSale = it.idSale,
                            isBuy = it.isBuy,
                            saleOrder = it.saleOrder,
                        )
                    } else {
                        it
                    }
                }
            )
        } else {
            selectedId = -1L
            _state.value = _state.value.copy(
                list = _state.value.list.map {
                    if (it is BuyListComponent) {
                        BuyListComponent(
                            it.id,
                            it.name,
                            it.home,
                            it.saleName,
                            if (it.id == item.id) false else it.isActiv,
                            idHome = it.idHome,
                            idSale = it.idSale,
                            isBuy = it.isBuy,
                            saleOrder = it.saleOrder,
                        )
                    } else {
                        it
                    }
                }
            )
        }
    }
}
