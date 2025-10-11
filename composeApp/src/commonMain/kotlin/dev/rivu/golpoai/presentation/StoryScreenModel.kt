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
import dev.rivu.golpoai.ai.GenerationType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class StoryScreenModel(
    private val useCase: StoryUseCase,
    private val saveStoryUseCase: SaveStoryUseCase,
    private val localGenerationSettings: LocalGenerationSettings
) : StateScreenModel<StoryUiState>(StoryUiState()) {

    val useLocalGeneration: StateFlow<Boolean> = localGenerationSettings
        .useLocalGenerationFlow
        .stateIn(screenModelScope, SharingStarted.Eagerly, localGenerationSettings.useLocalGeneration)

    val generationType: StateFlow<GenerationType> = localGenerationSettings
        .generationTypeFlow
        .stateIn(screenModelScope, SharingStarted.Eagerly, localGenerationSettings.generationType)

    val ollamaBaseUrl: StateFlow<String> = localGenerationSettings
        .ollamaBaseUrlFlow
        .stateIn(screenModelScope, SharingStarted.Eagerly, localGenerationSettings.ollamaBaseUrl)

    fun setUseLocalGeneration(enabled: Boolean) {
        screenModelScope.launch {
            localGenerationSettings.useLocalGeneration = enabled
        }
    }

    fun setGenerationType(type: GenerationType) {
        screenModelScope.launch {
            localGenerationSettings.generationType = type
        }
    }

    fun setOllamaBaseUrl(url: String) {
        screenModelScope.launch {
            localGenerationSettings.ollamaBaseUrl = url
        }
    }

    val isOfflineModelReady = useCase.isOfflineModelReady

    @OptIn(ExperimentalTime::class)
    fun generateStory(prompt: String, genre: String, language: String) {
        mutableState.value = mutableState.value.copy(loading = true, error = null)
        val currentGenerationType = generationType.value
        val currentOllamaUrl = ollamaBaseUrl.value
        screenModelScope.launch {
            try {
                val story = useCase.generateStory(
                    prompt = prompt,
                    genre = genre,
                    language = language,
                    generationType = currentGenerationType,
                    ollamaBaseUrl = currentOllamaUrl
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
                                isOffline = currentGenerationType == GenerationType.LOCAL,
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
