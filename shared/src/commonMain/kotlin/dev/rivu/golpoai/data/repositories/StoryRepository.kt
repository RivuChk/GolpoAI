package dev.rivu.golpoai.data.repositories

import dev.rivu.golpoai.ai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StoryRepository(
    private val onlineModel: GenerativeModel,
    private val offlineModel: GenerativeModel
) {

    val isOfflineModelReady: StateFlow<Boolean> = offlineModel.isReady

    suspend fun generateStory(prompt: String, offline: Boolean): Result<String> {
        val model = if (offline) offlineModel else onlineModel
        return model.generateStory(prompt)
    }
}
