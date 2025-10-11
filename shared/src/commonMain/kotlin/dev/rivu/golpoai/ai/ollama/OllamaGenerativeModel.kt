package dev.rivu.golpoai.ai.ollama

import dev.rivu.golpoai.ai.GenerativeModel
import dev.rivu.golpoai.logging.Logger
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OllamaGenerativeModel(
    private val baseUrl: String,
    private val modelName: String = "llama2" // Default model
) : GenerativeModel {

    private val apiService = OllamaApiService(baseUrl)
    private val _isReady = MutableStateFlow(false)

    override val isReady: StateFlow<Boolean> = _isReady

    init {
        // Check if Ollama service is available and model is ready
        checkModelAvailability()
    }

    override suspend fun generateStory(prompt: String, awaitReadiness: Boolean): Result<String> {
        if (!_isReady.value && !awaitReadiness) {
            return Result.failure(Exception("Ollama model is not ready"))
        }

        return try {
            val request = OllamaRequest(
                model = modelName,
                prompt = prompt,
                stream = false
            )

            Logger.d("Generating story with prompt: $prompt")

            val result = apiService.generateResponse(request)

            result.fold(
                onSuccess = { response ->
                    Logger.d("Story generated successfully")
                    Result.success(response.response)
                },
                onFailure = { error ->
                    Logger.e("Error generating story", error)
                    Result.failure(error)
                }
            )
        } catch (e: Exception) {
            Logger.e("Unexpected error generating story", e)
            Result.failure(e)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun checkModelAvailability() {

        if (baseUrl.isNotBlank()) {

            // Optionally, you could make an actual call to verify:
            kotlinx.coroutines.GlobalScope.launch {
                val result = apiService.getModels()
                _isReady.value = result.isSuccess
            }
        }
    }

    fun close() {
        apiService.close()
    }
}