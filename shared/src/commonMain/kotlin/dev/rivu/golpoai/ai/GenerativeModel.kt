package dev.rivu.golpoai.ai

interface GenerativeModel {
    suspend fun generateStory(prompt: String): Result<String>
}
