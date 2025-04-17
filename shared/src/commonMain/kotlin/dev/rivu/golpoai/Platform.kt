package dev.rivu.golpoai

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform