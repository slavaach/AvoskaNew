package ru.yandex.slavaach.nullapplicatoin.features.weather

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
import ru.yandex.slavaach.nullapplicatoin.R
import ru.yandex.slavaach.nullapplicatoin.component.topBar.ClickOnTitle
import ru.yandex.slavaach.nullapplicatoin.core.presentation.base.BaseFrgm

class WeatherFrgm : BaseFrgm(), Screen {

    @Composable
    override fun Content() {
        GetView()
    }

    @Composable
    fun GetView(
        viewModel: WeatherViewModel = koinViewModel(),
    ) {
        val state = remember { viewModel.state }
        val stateList = viewModel.stateOld.collectAsState()
        val allList = stateList.value.list
        LaunchedEffect(0) {
            viewModel.setTitleName("Погода")
            viewModel.setIconTitle(R.drawable.weather)
            viewModel.setSubTitleName("")
            viewModel.setClickOnTitle(ClickOnTitle.NOT_CLICK)
        }

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            items(allList.size) { position ->
                val item = allList.get(position)

                Row {
                    item.GetView(viewModel)
                }
                Spacer(modifier = Modifier.height(7.dp))
            }
        }
        state.value.bottomBar.getView(viewModel)()
    }
}
