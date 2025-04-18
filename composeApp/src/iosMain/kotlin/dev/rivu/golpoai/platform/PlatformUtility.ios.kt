package dev.rivu.golpoai.platform

import androidx.compose.runtime.Composable
import dev.rivu.golpoai.ContextWrapper
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.Foundation.NSUUID


actual fun PlatformUtility.shareStory(contextWrapper: ContextWrapper, story: String) {
    val content = "Here's a story generated with GolpoAI ✨\n\n$story"
    val activityController = UIActivityViewController(
        activityItems = listOf(content),
        applicationActivities = null
    )
    val rootVC = UIApplication.sharedApplication.keyWindow?.rootViewController
    rootVC?.presentViewController(activityController, animated = true, completion = null)
}

@Composable
actual fun PlatformUtility.getContext(): ContextWrapper {
    return ContextWrapper()
}

actual fun PlatformUtility.generateUUID(): String = NSUUID().UUIDString()
