package dev.rivu.golpoai

interface Platform {
    val name: String

    val platform: PlatformEnum
}

public enum class PlatformEnum {
    Android, iOS, JVM
}

expect fun getPlatform(): Platform

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class ContextWrapper
