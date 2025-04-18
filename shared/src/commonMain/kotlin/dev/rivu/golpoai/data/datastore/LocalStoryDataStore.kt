package dev.rivu.golpoai.data.datastore

import dev.rivu.golpoai.db.Story_history
import kotlinx.coroutines.flow.Flow

interface LocalStoryDataStore {
    fun getAllStories(): Flow<List<Story_history>>
    fun insertStory(story: Story_history)
}
