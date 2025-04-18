package dev.rivu.golpoai.data.repositories

import dev.rivu.golpoai.data.datastore.LocalStoryDataStore
import dev.rivu.golpoai.data.models.SavedStory
import dev.rivu.golpoai.db.Story_history
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoryHistoryRepository(
    private val localDataStore: LocalStoryDataStore
) {
    fun getAllStories(): Flow<List<SavedStory>> =
        localDataStore.getAllStories()
            .map { list ->
                list.map {
                    SavedStory(
                        id = it.id,
                        prompt = it.prompt,
                        genre = it.genre,
                        story = it.story,
                        createdAt = it.created_at
                    )
                }
            }

    fun insertStory(story: SavedStory) {
        localDataStore.insertStory(
            Story_history(
                id = story.id,
                prompt = story.prompt,
                genre = story.genre,
                story = story.story,
                created_at = story.createdAt
            )
        )
    }
}
