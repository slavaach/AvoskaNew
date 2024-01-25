package ru.yandex.slavaach.nullapplicatoin.component.list.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
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
import ru.yandex.slavaach.nullapplicatoin.core.theme.scaledSp
import ru.yandex.slavaach.nullapplicatoin.core.ui.CustomCheckbox
import ru.yandex.slavaach.nullapplicatoin.features.buy.BuyViewModel

data class BuyListComponent(
    val id: Long = 1,
    val name: String = "Ljv jhkh kjb kjb kb khb kb kjb kjb kj bkj bk ",
    val home: String = "",
    val saleName: String,
    val isActiv: Boolean = false,
    val isBuy: Boolean = false,
    val idHome: Long,
    val idSale: Long,
    val saleOrder: Int,
) : TypeListComponent {

    @Composable
    override fun GetView(
        viewModel: BaseViewModel,
    ) {
        GetViewInit(BuyData(id, name, home, saleName, isActiv, isBuy, idHome, idSale), viewModel)
    }

    data class BuyData(
        val id: Long,
        val name: String,
        val home: String,
        val saleName: String,
        val isActiv: Boolean,
        val isBuy: Boolean,
        val idHome: Long,
        val idSale: Long,
    ) :
        DataComponent
}

@Preview(widthDp = 420, heightDp = 100)
@Composable
private fun GetViewInit(
    data: BuyListComponent.BuyData = BuyListComponent.BuyData(
        1,
        "Ljv jhkh kjb kjb kb khb kb kjb kjb kj bkj bk ",
        "Дом",
        "рынок",
        false,
        false,
        0L,
        0L,
    ),
    viewModel: BaseViewModel = object : BaseViewModel() {},
) {
    if (viewModel !is BuyViewModel) return
    val checkedState = remember { mutableStateOf(data.isBuy) }
    val fontWeight = if (data.isActiv) FontWeight(FONT_WEIGHT_BOLD) else FontWeight(FONT_WEIGHT_REGULAR)
    Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(fraction = 1f)
                .padding(0.dp)
                .wrapContentHeight(),
        ) {
            Text(
                text = data.home,
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
            val configuration = LocalConfiguration.current
            val textWidth = (configuration.screenWidthDp - 132 - 20 * LocalDensity.current.fontScale).toInt()
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
                overflow = TextOverflow.Visible,
                modifier = Modifier.width(textWidth.dp)
            )
            Spacer(Modifier.weight(1f, true))
            CustomCheckbox(
                checked = data.isBuy,
                onCheckedChange = {
                    checkedState.value = it
                    viewModel.isBuy(data.id)
                },
                modifier = Modifier
                    .height(24.scaledSp)
                    .width(24.scaledSp),

                // colors = CheckboxDefaults.colors(checkedColor = Color.Transparent,
                //   checkmarkColor = MaterialTheme.colors.secondary)

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
