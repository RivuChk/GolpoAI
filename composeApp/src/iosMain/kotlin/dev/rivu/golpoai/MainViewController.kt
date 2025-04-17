package dev.rivu.golpoai

import androidx.compose.ui.window.ComposeUIViewController
import dev.rivu.golpoai.di.initKoin
import platform.UIKit.UIViewController

private var koinStarted = false

fun MainViewController(): UIViewController {
    if (!koinStarted) { // ✅ start Koin once for iOS here
        initKoin()
        koinStarted = true
    }
    return ComposeUIViewController { App() }
}
