package ru.yandex.slavaach.nullapplicatoin

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.yandex.slavaach.nullapplicatoin.core.presentation.InfoDialog
import ru.yandex.slavaach.nullapplicatoin.core.theme.MyApplicationTheme
import ru.yandex.slavaach.nullapplicatoin.features.custom.CustomFrgm
import ru.yandex.slavaach.nullapplicatoin.services.AppUnhandledExceptionHandler

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUnhandledExHanlder()
        viewModel.setViewModel(applicationContext)
        setContent {
            val state = remember { viewModel.state }
            Navigator(CustomFrgm()) {
                MyApplicationTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        Scaffold(
                            topBar = {
                                state.value.topBar.getView(viewModel)()
                            }
                        ) { padding ->
                            viewModel.setPadding(padding)
                            MainViews(viewModel)
                        }
                        // ScreenViewViewModelSample(viewModel)
                    }
                    if (state.value.openAlert) {
                        InfoDialog(
                            title = state.value.dataForAlert.title,
                            text = state.value.dataForAlert.text,
                            callbackOk = state.value.dataForAlert.callbackOk,
                            callbackNot = state.value.dataForAlert.callbackNot,
                            textOkButton = state.value.dataForAlert.textOkButton,
                            textNotButton = state.value.dataForAlert.textNotButton,
                            viewModel = viewModel,
                            contex = applicationContext,
                        )
                    }
                }
            }
        }
    }

    private fun setUnhandledExHanlder() {
        val currentHanlder = Thread.getDefaultUncaughtExceptionHandler()
        if (currentHanlder !is AppUnhandledExceptionHandler) {
            Thread.setDefaultUncaughtExceptionHandler(AppUnhandledExceptionHandler(this.javaClass.name))
        }
    }
}

@Composable
fun MainViews(viewModel: MainViewModel) {
    Box(
        modifier = with(Modifier) {
            val resors = if (isSystemInDarkTheme()) {
                R.drawable.paper_book_dark
            } else {
                R.drawable.paper_book
            }
            fillMaxSize()
                .paint(
                    // Replace with your image id
                    painterResource(id = resors),
                    contentScale = ContentScale.FillBounds
                )
        }
    ) {
        CurrentScreen()
        val navigator = LocalNavigator.currentOrThrow
        viewModel.setNav(navigator)
    }
}
