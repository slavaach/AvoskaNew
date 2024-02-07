package ru.yandex.slavaach.nullapplicatoin.features.reference

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.yandex.slavaach.nullapplicatoin.R
import ru.yandex.slavaach.nullapplicatoin.component.topBar.ClickOnTitle
import ru.yandex.slavaach.nullapplicatoin.core.presentation.base.BaseFrgm
import ru.yandex.slavaach.nullapplicatoin.core.ui.EnterAnimation
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.ReferenceUseCase

class ReferenceFrgm(
    val useCase: ReferenceUseCase,
    val name: String,
) : BaseFrgm(), Screen {

    @Composable
    override fun Content() {
        EnterAnimation{GetView(name = name)}
    }

    @Composable
    fun GetView(
        name: String,
        viewModel: ReferenceViewModel = koinViewModel { parametersOf(useCase,) },
    ) {
        viewModel.referenceUseCase = useCase
        viewModel.updateList()
        val state = remember { viewModel.state }
        val stateList = viewModel.stateOld.collectAsState()
        val allList = stateList.value.list

        LaunchedEffect(name) {
            viewModel.setTitleName(name)
            viewModel.setSubTitleName("")
            viewModel.setIconTitle(R.drawable.reference)
            viewModel.setClickOnTitle(ClickOnTitle.NOT_CLICK)
        }

        if (state.value.openAddDialog) {
            AddDialog(viewModel)
        }

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            items(allList.size) { position ->

                val item = allList.get(position)

                Row(
                    modifier = Modifier.clickable {
                        viewModel.setSelected(item)
                    }
                ) {
                    item.GetView(viewModel)
                }
                val paddingBottom = if (position == allList.size - 1) 80 else 7
                Spacer(modifier = Modifier.height(paddingBottom.dp))
            }
        }
        state.value.bottomBar.getView(viewModel)()
    }
}
