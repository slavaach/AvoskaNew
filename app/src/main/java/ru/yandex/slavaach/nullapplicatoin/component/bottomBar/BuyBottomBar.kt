package ru.yandex.slavaach.nullapplicatoin.component.bottomBar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomAppBar
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.yandex.slavaach.nullapplicatoin.R
import ru.yandex.slavaach.nullapplicatoin.core.presentation.base.BaseViewModel
import ru.yandex.slavaach.nullapplicatoin.core.presentation.base.TypeOnClick
import ru.yandex.slavaach.nullapplicatoin.core.theme.scaledSp
import ru.yandex.slavaach.nullapplicatoin.features.buy.BuyViewModel
import ru.yandex.slavaach.nullapplicatoin.features.buy.TypeOrder

@Composable
fun BuyBottomBar(viewModel: BaseViewModel) {
    var expanded = remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.fillMaxHeight()) {
        BottomAppBar(backgroundColor = MaterialTheme.colors.primary) {
            IconButton(onClick = {
                viewModel.onClick(OnClickSaveBuyBottomBar("добавить"))
            }) {
                Icon(
                    Icons.Filled.Save,
                    contentDescription = "Информация о приложении",
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .width(24.scaledSp)
                        .height(24.scaledSp),
                )
            }
            Box(
                modifier = Modifier
                    .padding(8.scaledSp, 8.scaledSp, 16.scaledSp, 8.scaledSp)
                    .width(32.scaledSp)
                    .height(32.scaledSp),
            ) {
                IconButton(onClick = {
                    expanded.value = true
                }) {
                    Icon(
                        painter = painterResource(id = R.mipmap.orders),
                        contentDescription = "сортировка",
                        tint = MaterialTheme.colors.secondary,
                        modifier = Modifier
                            .width(36.scaledSp)
                            .height(36.scaledSp),
                    )
                }
                BuyBottomBarDropdownMenu(viewModel, expanded)
            }
            Box(
                modifier = Modifier
                    .width(24.scaledSp)
                    .height(24.scaledSp),
            ) {
                IconButton(onClick = {
                    viewModel.onClick(OnClickFilterBuyBottomBar("добавить"))
                }) {
                    val icon = if ((viewModel as? BuyViewModel)?.state?.value?.filterBuy ?: false) R.drawable.filter_full else R.drawable.basket_fill
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "фильтр",
                        tint = MaterialTheme.colors.secondary,
                        modifier = Modifier
                            .width(24.scaledSp)
                            .height(24.scaledSp),
                    )
                }
            }
            Spacer(Modifier.weight(1f, true))
        }
    }
}

data class OnClickSaveBuyBottomBar(val name: String) : TypeOnClick

data class OnClickOrderBuyBottomBar(val typeOrder: TypeOrder) : TypeOnClick

data class OnClickFilterBuyBottomBar(val name: String) : TypeOnClick

@Composable
fun BuyBottomBarDropdownMenu(viewModel: BaseViewModel, expanded: MutableState<Boolean>) {
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .border(BorderStroke(width = 1.dp, color = MaterialTheme.colors.surface))
            .shadow(elevation = 2.dp)
            .width(200.dp)
            .padding(4.dp),
        // modifier = Modifier.padding(12.dp) // .border(2.dp, Color.Red)
    ) {
        Text(
            "по id заказа",
            fontSize = 18.sp,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    expanded.value = false
                    viewModel.onClick(OnClickOrderBuyBottomBar(TypeOrder.ID))
                })
                .padding(12.dp)
        )

        Text(
            "по сортировке магазина и id заказа",
            fontSize = 18.sp,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    expanded.value = false
                    viewModel.onClick(OnClickOrderBuyBottomBar(TypeOrder.SALE_ID_CUSTOM))
                })
                .padding(12.dp)
        )

        Text(
            "по сортировке магазина и имени заказа",
            fontSize = 18.sp,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    expanded.value = false
                    viewModel.onClick(OnClickOrderBuyBottomBar(TypeOrder.SALE_NAME))
                })
                .padding(12.dp)
        )
    }
}
