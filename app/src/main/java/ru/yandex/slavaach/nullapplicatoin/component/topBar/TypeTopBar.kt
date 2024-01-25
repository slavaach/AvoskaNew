package ru.yandex.slavaach.nullapplicatoin.component.topBar

import androidx.compose.runtime.Composable
import ru.yandex.slavaach.nullapplicatoin.MainViewModel

enum class TypeTopBar {
    DEFAULT {
        @Composable
        override fun getView(viewModel: MainViewModel): @Composable () -> Unit {
            return { DefaltTopAppBar(viewModel) }
        }
    },
    ;

    @Composable
    open fun getView(viewModel: MainViewModel): @Composable () -> Unit {
        return { DefaltTopAppBar(viewModel) }
    }
}
