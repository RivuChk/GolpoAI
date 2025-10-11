package dev.rivu.golpoai.domain

import dev.rivu.golpoai.ai.GenerationType
import dev.rivu.golpoai.data.repositories.StoryRepository
import kotlinx.coroutines.flow.StateFlow

class StoryUseCase(private val repository: StoryRepository) {
    val isOfflineModelReady: StateFlow<Boolean> = repository.isOfflineModelReady
    
    suspend fun generateStory(
        prompt: String, 
        genre: String, 
        language: String, 
        generationType: GenerationType,
        ollamaBaseUrl: String = ""
    ): Result<String> {
        val fullPrompt = "Write a $genre story in $language: $prompt"
        return repository.generateStory(fullPrompt, generationType, ollamaBaseUrl)
    }
    
    // Legacy method for backward compatibility
    suspend fun generateStory(prompt: String, genre: String, language: String, offline: Boolean = false): Result<String> {
        val generationType = if (offline) GenerationType.LOCAL else GenerationType.ONLINE_GEMINI
        return generateStory(prompt, genre, language, generationType)
    }
}
