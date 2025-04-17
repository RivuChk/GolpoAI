package dev.rivu.golpoai.platform

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dev.rivu.golpoai.ContextWrapper

actual fun PlatformUtility.shareStory(contextWrapper: ContextWrapper, story: String) {
    val context = contextWrapper.context
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Generated with GolpoAI")
        putExtra(Intent.EXTRA_TEXT, "Here's a story generated with GolpoAI ✨\n\n$story")
    }
    val chooser = Intent.createChooser(intent, "Share via")
    context.startActivity(chooser)
}


@Composable
actual fun getContext(): ContextWrapper {
    return ContextWrapper(LocalContext.current)
}
