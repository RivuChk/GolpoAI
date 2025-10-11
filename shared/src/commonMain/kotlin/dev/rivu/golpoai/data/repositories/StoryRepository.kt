package dev.rivu.golpoai.data.repositories

import dev.rivu.golpoai.ai.GenerativeModel
import dev.rivu.golpoai.ai.GenerationType
import dev.rivu.golpoai.ai.ollama.OllamaGenerativeModel
import kotlinx.coroutines.flow.StateFlow

class StoryRepository(
    private val onlineModel: GenerativeModel,
    private val offlineModel: GenerativeModel
) {

    val isOfflineModelReady: StateFlow<Boolean> = offlineModel.isReady
    
    // Cache for Ollama models to avoid recreating them
    private val ollamaModels = mutableMapOf<String, OllamaGenerativeModel>()

    suspend fun generateStory(prompt: String, generationType: GenerationType, ollamaBaseUrl: String = ""): Result<String> {
        val model = when (generationType) {
            GenerationType.LOCAL -> offlineModel
            GenerationType.ONLINE_GEMINI -> onlineModel
            GenerationType.OLLAMA -> {
                if (ollamaBaseUrl.isBlank()) {
                    return Result.failure(Exception("Ollama base URL is required for Ollama generation"))
                }
                getOrCreateOllamaModel(ollamaBaseUrl)
            }
        }
        
        return model.generateStory(prompt)
    }
    
    // Legacy method for backward compatibility
    suspend fun generateStory(prompt: String, offline: Boolean): Result<String> {
        val generationType = if (offline) GenerationType.LOCAL else GenerationType.ONLINE_GEMINI
        return generateStory(prompt, generationType)
    }
    
    private fun getOrCreateOllamaModel(baseUrl: String): OllamaGenerativeModel {
        return ollamaModels.getOrPut(baseUrl) {
            OllamaGenerativeModel(baseUrl)
        }
    }
    
    fun cleanupOllamaModels() {
        ollamaModels.values.forEach { it.close() }
        ollamaModels.clear()
    }
}
