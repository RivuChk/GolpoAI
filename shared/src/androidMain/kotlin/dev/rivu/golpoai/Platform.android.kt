package dev.rivu.golpoai

import android.content.Context
import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val platform: PlatformEnum = PlatformEnum.Android
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual class ContextWrapper(val context: Context)
