package ru.yandex.slavaach.nullapplicatoin.component.list.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.yandex.slavaach.nullapplicatoin.component.list.DataComponent
import ru.yandex.slavaach.nullapplicatoin.component.list.TypeListComponent
import ru.yandex.slavaach.nullapplicatoin.core.presentation.base.BaseViewModel

class WeatherListConponent(val name: String) : TypeListComponent {

    @Composable
    override fun GetView(
        viewModel: BaseViewModel,
    ) {
        GetViewInit(WeatherData(name), viewModel)
    }

    data class WeatherData(val name: String) : DataComponent
}

@Preview(widthDp = 320, heightDp = 100)
@Composable
private fun GetViewInit(
    data: WeatherListConponent.WeatherData = WeatherListConponent.WeatherData("Ljv jhkh kjb kjb kb khb kb kjb kjb kj bkj bk "),
    viewModel: BaseViewModel = object : BaseViewModel() {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )
        Text(
            text = data.name,
            fontSize = 20.sp,
            color = MaterialTheme.colors.secondary,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = MaterialTheme.colors.surface)
        )
    }
}
