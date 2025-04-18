package dev.rivu.golpoai.domain

import dev.rivu.golpoai.data.models.SavedStory
import dev.rivu.golpoai.data.repositories.StoryHistoryRepository

class SaveStoryUseCase(
    private val repository: StoryHistoryRepository
) {
    suspend fun save(story: SavedStory) {
        repository.insertStory(story)
    }
}
