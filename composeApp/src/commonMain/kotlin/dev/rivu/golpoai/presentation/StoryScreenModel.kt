package dev.rivu.golpoai.presentation

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.rivu.golpoai.data.models.SavedStory
import dev.rivu.golpoai.data.models.Story
import dev.rivu.golpoai.data.models.StoryMetadata
import dev.rivu.golpoai.domain.SaveStoryUseCase
import dev.rivu.golpoai.domain.StoryUseCase
import dev.rivu.golpoai.logging.Logger
import dev.rivu.golpoai.platform.PlatformUtility
import dev.rivu.golpoai.platform.generateUUID
import dev.rivu.golpoai.preferences.LocalGenerationSettings
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class StoryScreenModel(
    private val useCase: StoryUseCase,
    private val saveStoryUseCase: SaveStoryUseCase,
    private val localGenerationSettings: LocalGenerationSettings
) : StateScreenModel<StoryUiState>(StoryUiState()) {

    val useLocalGeneration: StateFlow<Boolean> = localGenerationSettings
        .useLocalGenerationFlow
        .stateIn(screenModelScope, SharingStarted.Eagerly, localGenerationSettings.useLocalGeneration)

    fun setUseLocalGeneration(enabled: Boolean) {
        screenModelScope.launch {
            localGenerationSettings.useLocalGeneration = enabled
        }
    }

    val isOfflineModelReady = useCase.isOfflineModelReady

    fun generateStory(prompt: String, genre: String, language: String) {
        mutableState.value = mutableState.value.copy(loading = true, error = null)
        val isOffline = useLocalGeneration.value
        screenModelScope.launch {
            try {
                val story = useCase.generateStory(
                    prompt = prompt,
                    genre = genre,
                    language = language,
                    offline = isOffline
                )
                mutableState.value = if (story.isSuccess) {
                    mutableState.value.copy(
                        error = null,
                        loading = false,
                        saved = false,
                        story = Story(
                            storyText = story.getOrThrow(),
                            storyMetadata = StoryMetadata(
                                title = prompt,
                                prompt = prompt,
                                genre = genre,
                                language = language,
                                isOffline = isOffline,
                                createdAt = Clock.System.now().toEpochMilliseconds(),
                            ),
                        ),
                    )
                } else {
                    throw story.exceptionOrNull() ?: Exception("Unknown error")
                }
            } catch (e: Exception) {
                Logger.e("Error generating story", e)
                mutableState.value = mutableState.value.copy(
                    error = e.message ?: "Unknown error",
                    loading = false,
                    saved = false
                )
            }
        }
    }

    fun saveStory(story: Story) {
        val id = PlatformUtility.generateUUID()
        val savedStory = SavedStory(id, story)
        screenModelScope.launch {
            saveStoryUseCase.save(savedStory)
            mutableState.value = mutableState.value.copy(saved = true)
        }
    }
}
