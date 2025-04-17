package dev.rivu.golpoai.presentation

import cafe.adriel.voyager.core.model.StateScreenModel
import dev.rivu.golpoai.domain.StoryUseCase
import cafe.adriel.voyager.core.model.screenModelScope
import dev.rivu.golpoai.logging.Logger
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

data class StoryUiState(
    val prompt: String = "",
    val genre: String = "",
    val story: String? = null,
    val loading: Boolean = false,
    val error: String? = null
)

class StoryScreenModel(
    private val useCase: StoryUseCase
) : StateScreenModel<StoryUiState>(StoryUiState()) {

    fun generateStory(prompt: String, genre: String, language: String) {
        mutableState.value = mutableState.value.copy(loading = true, error = null)
        screenModelScope.launch {
            try {
                val story = useCase.generateStory(prompt, genre, language)
                mutableState.value = if (story.isSuccess) {
                    mutableState.value.copy(
                        story = story.getOrThrow(),
                        loading = false
                    )
                } else {
                    throw story.exceptionOrNull() ?: Exception("Unknown error")
                }
            } catch (e: Exception) {
                Logger.e("Error generating story", e)
                mutableState.value = mutableState.value.copy(
                    error = e.message ?: "Unknown error",
                    loading = false
                )
            }
        }
    }
}
