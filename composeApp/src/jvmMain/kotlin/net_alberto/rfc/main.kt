package net_alberto.rfc

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "RFC",
        state = rememberWindowState(
            width = 350.dp,
            height = 400.dp
        )
    ) {
        App()
    }
}