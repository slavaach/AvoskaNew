package ru.yandex.slavaach.nullapplicatoin.component.bottomBar

import androidx.compose.runtime.Composable
import ru.yandex.slavaach.nullapplicatoin.core.presentation.base.BaseViewModel

enum class TypeBottomBar {
    DEFAULT {
        @Composable
        override fun getView(viewModel: BaseViewModel): @Composable () -> Unit {
            return { DefaltBottomBar(viewModel) }
        }
    },
    NULL {
        @Composable
        override fun getView(viewModel: BaseViewModel): @Composable () -> Unit {
            return { NullBottomBar(viewModel) }
        }
    },
    BUY {
        @Composable
        override fun getView(viewModel: BaseViewModel): @Composable () -> Unit {
            return { BuyBottomBar(viewModel) }
        }
    },
    ;

    @Composable
    open fun getView(viewModel: BaseViewModel): @Composable () -> Unit {
        return { DefaltBottomBar(viewModel) }
    }
}
