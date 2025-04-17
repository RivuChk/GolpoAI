package dev.rivu.golpoai.domain

import dev.rivu.golpoai.data.StoryRepository

class StoryUseCase(private val repository: StoryRepository) {
    suspend fun generateStory(prompt: String, genre: String, language: String): String {
        val fullPrompt = "Write a $genre story in $language: $prompt"
        return repository.getStory(fullPrompt)
    }
}
