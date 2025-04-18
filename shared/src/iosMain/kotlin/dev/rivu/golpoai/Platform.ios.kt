package dev.rivu.golpoai

import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val platform: PlatformEnum = PlatformEnum.iOS
}

actual fun getPlatform(): Platform = IOSPlatform()

actual class ContextWrapper
