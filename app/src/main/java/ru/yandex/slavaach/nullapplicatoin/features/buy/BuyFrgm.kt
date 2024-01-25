package ru.yandex.slavaach.nullapplicatoin.features.buy

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import ru.yandex.slavaach.nullapplicatoin.R
import ru.yandex.slavaach.nullapplicatoin.component.topBar.ClickOnTitle
import ru.yandex.slavaach.nullapplicatoin.core.presentation.base.BaseFrgm

class BuyFrgm : BaseFrgm(), Screen {

    @Composable
    override fun Content() {
        GetView()
    }

    @Composable
    fun GetView(
        viewModel: BuyViewModel = koinViewModel(),
    ) {
        val state = remember { viewModel.state }
        val stateList = viewModel.stateOld.collectAsState()
        val allList = stateList.value.list

        LaunchedEffect(0) {
            viewModel.setSubTitleName("Покупаю")
            viewModel.setIconTitle(R.drawable.shoping_ok)
            viewModel.setTitleName()
            viewModel.setClickOnTitle(ClickOnTitle.CLICK_MULTI)
        }

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .fillMaxSize(),
        ) {
            items(allList.size) { position ->

                val item = allList.get(position)

                Row(
                    modifier = Modifier.padding(0.dp).clickable {
                        viewModel.setSelected(item)
                    },
                ) {
                    item.GetView(viewModel)
                }
                val paddingBottom = if (position == allList.size - 1) 80 else 0
                Spacer(modifier = Modifier.height(paddingBottom.dp))
            }
        }
        state.value.bottomBar.getView(viewModel)()
    }
}
