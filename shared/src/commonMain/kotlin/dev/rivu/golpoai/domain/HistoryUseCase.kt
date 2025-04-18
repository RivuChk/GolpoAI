package dev.rivu.golpoai.domain

import dev.rivu.golpoai.data.models.SavedStory
import dev.rivu.golpoai.data.repositories.StoryHistoryRepository
import kotlinx.coroutines.flow.Flow

class HistoryUseCase(
    private val repository: StoryHistoryRepository
) {
    fun getAllStories(): Flow<List<SavedStory>> = repository.getAllStories()
}
