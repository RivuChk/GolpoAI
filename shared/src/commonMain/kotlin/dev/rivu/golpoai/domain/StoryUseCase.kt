package dev.rivu.golpoai.domain

import dev.rivu.golpoai.data.repositories.StoryRepository

class StoryUseCase(private val repository: StoryRepository) {
    suspend fun generateStory(prompt: String, genre: String, language: String): Result<String> {
        val fullPrompt = "Write a $genre story in $language: $prompt"
        return repository.getStory(fullPrompt)
    }
}
