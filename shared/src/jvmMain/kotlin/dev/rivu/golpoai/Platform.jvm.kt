package dev.rivu.golpoai

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
    override val platform: PlatformEnum = PlatformEnum.JVM
}

actual fun getPlatform(): Platform = JVMPlatform()

actual class ContextWrapper
