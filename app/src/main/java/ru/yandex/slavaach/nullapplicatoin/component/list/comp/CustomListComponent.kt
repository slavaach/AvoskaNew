package ru.yandex.slavaach.nullapplicatoin.component.list.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.yandex.slavaach.nullapplicatoin.component.list.DataComponent
import ru.yandex.slavaach.nullapplicatoin.component.list.TypeListComponent
import ru.yandex.slavaach.nullapplicatoin.core.Constant.FONT_WEIGHT_BOLD
import ru.yandex.slavaach.nullapplicatoin.core.Constant.FONT_WEIGHT_REGULAR
import ru.yandex.slavaach.nullapplicatoin.core.presentation.base.BaseViewModel
import ru.yandex.slavaach.nullapplicatoin.core.theme.nonScaledSp

data class CustomListComponent(
    val id: Long = 1,
    val name: String = "Ljv jhkh kjb kjb kb khb kb kjb kjb kj bkj bk ",
    val order: Int = 0,
    val saleName: String,
    val isActiv: Boolean = false,
) : TypeListComponent {

    @Composable
    override fun GetView(
        viewModel: BaseViewModel,
    ) {
        GetViewInit(CustomData(id, name, order, saleName, isActiv), viewModel)
    }

    data class CustomData(val id: Long, val name: String, val order: Int, val saleName: String, val isActiv: Boolean) : DataComponent
}

@Preview(widthDp = 320, heightDp = 100)
@Composable
private fun GetViewInit(
    data: CustomListComponent.CustomData = CustomListComponent.CustomData(1, "Ljv jhkh kjb kjb kb khb kb kjb kjb kj bkj bk ", 1, "рынок", false),
    viewModel: BaseViewModel = object : BaseViewModel() {},
) {
    val fontWeight = if (data.isActiv) FontWeight(FONT_WEIGHT_BOLD) else FontWeight(FONT_WEIGHT_REGULAR)
    Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(fraction = 1f)
                .padding(0.dp)
                .wrapContentHeight(),
        ) {
            Text(
                text = data.id.toString(),
                fontSize = 12.sp.nonScaledSp,
                fontWeight = fontWeight,
                color = MaterialTheme.colors.secondary,
                overflow = TextOverflow.Visible
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(fraction = 1f)
                .height(IntrinsicSize.Max)
                .wrapContentHeight(),

            // horizontalArrangement = Arrangement.Start,
        ) {
            Text(
                text = data.saleName,
                fontSize = 20.sp,
                fontWeight = fontWeight,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.width(100.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Text(
                text = data.name,
                fontSize = 20.sp,
                fontWeight = fontWeight,
                color = MaterialTheme.colors.secondary,
                overflow = TextOverflow.Visible
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = MaterialTheme.colors.surface)
        )
    }
}
