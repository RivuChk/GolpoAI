package dev.rivu.golpoai.domain

import dev.rivu.golpoai.data.repositories.StoryRepository
import kotlinx.coroutines.flow.StateFlow

class StoryUseCase(private val repository: StoryRepository) {
    val isOfflineModelReady: StateFlow<Boolean> = repository.isOfflineModelReady
    suspend fun generateStory(prompt: String, genre: String, language: String, offline: Boolean = false): Result<String> {
        val fullPrompt = "Write a $genre story in $language: $prompt"
        return repository.generateStory(fullPrompt, offline)
    }
}
