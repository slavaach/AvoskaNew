package ru.yandex.slavaach.nullapplicatoin.component.list

import androidx.compose.runtime.Composable
import ru.yandex.slavaach.nullapplicatoin.core.presentation.base.BaseViewModel

interface TypeListComponent {
    @Composable
    fun GetView(viewModel: BaseViewModel) {}
}

interface DataComponent
