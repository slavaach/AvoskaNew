package ru.yandex.slavaach.nullapplicatoin.component.topBar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.yandex.slavaach.nullapplicatoin.MainViewModel
import ru.yandex.slavaach.nullapplicatoin.R
import ru.yandex.slavaach.nullapplicatoin.core.Constant.FONT_WEIGHT_BOLD
import ru.yandex.slavaach.nullapplicatoin.core.Constant.FONT_WEIGHT_REGULAR
import ru.yandex.slavaach.nullapplicatoin.core.theme.DarkIconPalette
import ru.yandex.slavaach.nullapplicatoin.core.theme.ListIconDarkLite
import ru.yandex.slavaach.nullapplicatoin.core.theme.LiteIconPalette
import ru.yandex.slavaach.nullapplicatoin.core.theme.nonScaledSp
import ru.yandex.slavaach.nullapplicatoin.core.theme.pulsateClick
import ru.yandex.slavaach.nullapplicatoin.core.theme.scaledSp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DefaltTopAppBar(viewModel: MainViewModel) {
    val expanded = remember { mutableStateOf(false) }
    val state = remember { viewModel.state }
    val configuration = LocalConfiguration.current
    val textWidth = (((configuration.screenWidthDp - 114) / LocalDensity.current.fontScale) - 27).toInt()
    val topBarHeight = 24.dp + 30.scaledSp
    val expandedHome = remember { mutableStateOf(false) }
    val stateList = viewModel.stateHome.collectAsState()
    val allList = stateList.value.list

    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.height(topBarHeight),
    ) {
        IconButton(onClick = { }) {
            val theme = isSystemInDarkTheme()
            val icon = if (theme) DarkIconPalette else LiteIconPalette
            Icon(
                painter = painterResource(id = icon.get(ListIconDarkLite.IC_LAUNCHER) ?: R.drawable.ic_launcher),
                modifier = Modifier
                    .width(27.scaledSp)
                    .height(36.scaledSp),
                tint = Color.Unspecified,
                contentDescription = "Меню"
            )
        }
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.width(textWidth.scaledSp)
        ) {
            Text("Авоська ${state.value.subTitle}", fontSize = 16.sp.nonScaledSp, color = MaterialTheme.colors.secondary)
            Box {
                Text(
                    if (state.value.name.isBlank()) "Надо что-то выбрать" else state.value.name,
                    fontSize = 22.sp,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .basicMarquee(iterations = Int.MAX_VALUE)
                        .clickable(onClick = {
                            if (state.value.clickOnTitle.onClicing) {
                                if (allList.size > 0) expandedHome.value = true
                            }
                        }),
                )
                DefaltTopAppBarDropdownMenuHome(viewModel, expandedHome, state)
            }
        }
        Spacer(Modifier.weight(1f, true))
        IconButton(
            onClick = { },
            modifier = Modifier
                .padding(0.dp, 8.dp, 0.dp, 20.dp)
                .width(44.dp)
                .height(32.dp)
        ) {
            IconButton(onClick = {
                when (state.value.clickOnTitle) {
                    ClickOnTitle.CLICK_ONE -> {
                        viewModel.openBuy()
                    }

                    ClickOnTitle.CLICK_MULTI -> {
                        viewModel.openCustom()
                    }
                    else -> {}
                }
            }) {
                Icon(
                    painterResource(state.value.icon),
                    contentDescription = "Информация о приложении",
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .width(40.scaledSp)
                        .height(40.scaledSp),
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(0.dp, 8.dp, 0.dp, 20.dp)
                .width(44.dp)
                .height(32.dp),
        ) {
            IconButton(onClick = { expanded.value = true }) {
                Icon(
                    Icons.Filled.MoreVert,
                    contentDescription = "Меню",
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp),
                )
            }
            DefaltTopAppBarDropdownMenu(viewModel, expanded)
        }
    }
}

@Composable
fun DefaltTopAppBarDropdownMenuHome(viewModel: MainViewModel, expandedHome: MutableState<Boolean>, state: MutableState<MainViewModel.State>) {
    val stateList = viewModel.stateHome.collectAsState()
    val allList = stateList.value.list

    DropdownMenu(
        expanded = expandedHome.value,
        onDismissRequest = {
            if (state.value.clickOnTitle == ClickOnTitle.CLICK_MULTI) viewModel.cancelHomeForBuy()
            expandedHome.value = false
        },
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .border(BorderStroke(width = 1.dp, color = MaterialTheme.colors.surface))
            .shadow(elevation = 2.dp)
            .width(200.dp)
            .padding(4.dp),
        // modifier = Modifier.padding(12.dp) // .border(2.dp, Color.Red)
    ) {
        val heightDropdownMenu = (20.scaledSp + 24.dp) * (allList.size + state.value.clickOnTitle.padding) + 16.dp
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .height(heightDropdownMenu)
                .width(500.dp)
        ) {
            items(allList.size) { position ->
                val item = allList.get(position)
                val fontWeight = when (state.value.clickOnTitle) {
                    ClickOnTitle.CLICK_ONE -> if (state.value.idHomeForCustom == item.id) {
                        FontWeight(FONT_WEIGHT_BOLD)
                    } else {
                        FontWeight(FONT_WEIGHT_REGULAR)
                    }

                    ClickOnTitle.CLICK_MULTI -> if (state.value.idHomeForBuy.contains(item.id)) {
                        FontWeight(FONT_WEIGHT_BOLD)
                    } else {
                        FontWeight(FONT_WEIGHT_REGULAR)
                    }

                    else -> FontWeight(FONT_WEIGHT_REGULAR)
                }
                if (state.value.idHomeForCustom == item.id) FontWeight(FONT_WEIGHT_BOLD) else FontWeight(FONT_WEIGHT_REGULAR)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = if (item.name.isNullOrBlank()) "Надо что-то выбрать" else item.name,
                        fontSize = 20.sp,
                        color = MaterialTheme.colors.secondary,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = fontWeight,
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (state.value.clickOnTitle == ClickOnTitle.CLICK_ONE) expandedHome.value = false
                                viewModel.setHomeForCustom(item.id, item.name)
                            }.padding(8.dp)
                    )
                    if (state.value.clickOnTitle.padding > 0 && position == allList.size - 1) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            modifier = Modifier
                                .pulsateClick()
                                .fillMaxWidth()
                                .padding(4.dp),
                            onClick = {
                                viewModel.setHomeForBuy()
                                expandedHome.value = false
                            },
                        ) {
                            Text(
                                text = LocalContext.current.getString(R.string.choose_homes),
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.secondary,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DefaltTopAppBarDropdownMenu(viewModel: MainViewModel, expanded: MutableState<Boolean>) {
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
            "Лист закупок",
            fontSize = 18.sp,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    expanded.value = false

                    viewModel.openCustom()
                })
                .padding(12.dp)
        )

        Text(
            "Покупаю",
            fontSize = 18.sp,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    expanded.value = false

                    viewModel.openBuy()
                })
                .padding(12.dp)
        )

        Text(
            "Дома",
            fontSize = 18.sp,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    expanded.value = false

                    viewModel.openHome()
                })
                .padding(12.dp)
        )
        Text(
            "Магазины",
            fontSize = 18.sp,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    expanded.value = false
                    viewModel.openSale()
                })
                .padding(10.dp)
        )
        Divider()
        Text(
            "Погода",
            fontSize = 18.sp,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    expanded.value = false
                    viewModel.openWeather()
                })
                .padding(10.dp)
        )
    }
}
