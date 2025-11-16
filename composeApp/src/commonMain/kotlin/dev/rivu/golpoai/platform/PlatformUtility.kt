package dev.rivu.golpoai.platform

import androidx.compose.runtime.Composable
import dev.rivu.golpoai.ContextWrapper

object PlatformUtility {

}

expect fun PlatformUtility.shareStory(contextWrapper: ContextWrapper, story: String, imageUri: String? = null)

expect fun PlatformUtility.generateUUID(): String

@Composable
expect fun PlatformUtility.getContext(): ContextWrapper
