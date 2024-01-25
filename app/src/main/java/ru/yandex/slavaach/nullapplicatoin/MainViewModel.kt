package ru.yandex.slavaach.nullapplicatoin

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import ru.yandex.slavaach.nullapplicatoin.component.topBar.ClickOnTitle
import ru.yandex.slavaach.nullapplicatoin.component.topBar.TypeTopBar
import ru.yandex.slavaach.nullapplicatoin.core.presentation.DataForAlert
import ru.yandex.slavaach.nullapplicatoin.core.presentation.base.BaseViewModel
import ru.yandex.slavaach.nullapplicatoin.features.custom.data.SettingUseCase
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.home.Home
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.home.HomeUseCase
import ru.yandex.slavaach.nullapplicatoin.navigation.HomeNav
import java.lang.ref.WeakReference

class MainViewModel(
    val mainViewModelHolder: MainViewModelHolder,
    val activityContextHolder: ActivityContextHolder,
    val homeNav: HomeNav,
    var homeUseCase: HomeUseCase,
    val settingUseCase: SettingUseCase,
) : BaseViewModel(), KoinComponent {

    val state = mutableStateOf(
        State(
            topBar = TypeTopBar.DEFAULT,
            openAlert = false,
            dataForAlert = DataForAlert(
                title = null,
                text = null,
                callbackOk = null,
                callbackNot = null,
                textOkButton = null,
                textNotButton = null,
            ),
            name = "",
            padding = PaddingValues(),
            subTitle = "",
            clickOnTitle = ClickOnTitle.NOT_CLICK,
            idHomeForCustom = 0,
            nameHomeForCustom = "",
            icon = R.drawable.shoping_set,
            idHomeForBuy = listOf(),
        )
    )

    private var listHomeForBuy: List<Long> = listOf()
    init {
        super.onInit()
        updateList()
        mainViewModelHolder.viewModelRef = WeakReference(this)
    }

    fun setViewModel(context: Context) {
        activityContextHolder.contextRef = WeakReference(context)
    }

    data class HomeList(val list: List<Home>)

    val _home = MutableStateFlow(
        HomeList(listOf())
    )

    val stateHome: StateFlow<HomeList> = _home.asStateFlow()

    fun setHomeForBuy() {
        viewModelScope.launch { settingUseCase.update(state.value.idHomeForBuy) }
        setTitleNameMulti()
        setUpDateSettingBuy?.update(state.value.idHomeForBuy)
        listHomeForBuy = state.value.idHomeForBuy
    }

    fun cancelHomeForBuy() {
        state.value = state.value.copy(idHomeForBuy = listHomeForBuy)
        setTitleNameMulti()
    }

    fun setHomeForCustom(id: Long, name: String) {
        when (state.value.clickOnTitle) {
            ClickOnTitle.CLICK_ONE -> {
                viewModelScope.launch { settingUseCase.update(id) }
                state.value = state.value.copy(idHomeForCustom = id, nameHomeForCustom = name)
                setTitleName(name)
                setUpDateSettingHome?.update(id)
            }
            ClickOnTitle.CLICK_MULTI -> {
                val lisBuy = ArrayList(state.value.idHomeForBuy)
                val isSelect = lisBuy.contains(id)
                if (isSelect) {
                    lisBuy.remove(id)
                    state.value = state.value.copy(idHomeForBuy = lisBuy)
                } else {
                    lisBuy.add(id)
                    state.value = state.value.copy(idHomeForBuy = lisBuy)
                }
            }
            else -> {}
        }
    }
    fun updateList() {
        viewModelScope.launch {
            _home.value =
                _home.value.copy(list = homeUseCase.selectHome().map { Home(it.id, it.name, it.order) })
            settingUseCase.select()?.let { sh ->
                _home.value.list.firstOrNull { sh.idHome == it.id }?.let {
                    state.value = state.value.copy(idHomeForCustom = it.id, nameHomeForCustom = it.name)
                    if (state.value.clickOnTitle == ClickOnTitle.CLICK_ONE) setTitleName(it.name)
                }
                state.value = state.value.copy(idHomeForBuy = sh.idBuyHome)
                listHomeForBuy = sh.idBuyHome
            }
        }
    }

    fun clouseAllert() {
        state.value = state.value.copy(openAlert = false)
    }

    fun setClickOnTitle(clickOnTitle: ClickOnTitle) {
        state.value = state.value.copy(clickOnTitle = clickOnTitle)
    }

    fun setIconTitle(icon: Int) {
        state.value = state.value.copy(icon = icon)
    }

    data class State(
        val topBar: TypeTopBar,
        val openAlert: Boolean,
        val dataForAlert: DataForAlert,
        val name: String,
        val padding: PaddingValues,
        val subTitle: String,
        val clickOnTitle: ClickOnTitle,
        val idHomeForCustom: Long,
        val nameHomeForCustom: String,
        val icon: Int,
        val idHomeForBuy: List<Long>
    )

    fun setTitleName(name: String) {
        state.value = state.value.copy(name = name)
    }

    fun setTitleNameMulti() {
        val st: StringBuilder = StringBuilder("")
        state.value.idHomeForBuy.forEach { buy ->
            st.append(_home.value.list.firstOrNull { buy == it.id }?.name).append(" ")
        }

        state.value = state.value.copy(name = st.toString())
    }

    fun setSubTitleName(name: String) {
        state.value = state.value.copy(subTitle = name)
    }

    fun setPadding(padding: PaddingValues) {
        state.value = state.value.copy(padding = padding)
    }

    var navigator: Navigator? = null

    fun setNav(mNavigator: Navigator) {
        navigator = mNavigator
    }

    fun openHome() {
        homeNav.openHome()
    }

    fun openCustom() {
        homeNav.openCustom()
    }

    fun openBuy() {
        homeNav.openBuy()
    }

    fun openSale() {
        homeNav.openSale()
    }

    fun openWeather() {
        homeNav.openWeather()
    }
    var setUpDateSettingHome: upDateSettingHome? = null
    var setUpDateSettingBuy: upDateSettingBuy? = null

    interface upDateSettingHome {
        fun update(id: Long)
    }

    interface upDateSettingBuy {
        fun update(id: List<Long>)
    }
}
