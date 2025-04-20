package dev.rivu.golpoai.data.repositories

import dev.rivu.golpoai.data.datastore.LocalStoryDataStore
import dev.rivu.golpoai.data.models.SavedStory
import dev.rivu.golpoai.data.models.Story
import dev.rivu.golpoai.data.models.StoryMetadata
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
                        Story(
                            storyText = it.story,
                            storyMetadata = StoryMetadata(
                                title = "",
                                prompt = it.prompt,
                                genre = it.genre,
                                language = it.language ?: "English",
                                isOffline = it.is_offline == 1L,
                                createdAt = it.created_at
                            )
                        )
                    )
                }
            }

    fun insertStory(story: SavedStory) {
        localDataStore.insertStory(
            Story_history(
                id = story.id,
                prompt = story.story.storyMetadata.prompt,
                genre = story.story.storyMetadata.genre,
                story = story.story.storyText,
                created_at = story.story.storyMetadata.createdAt,
                is_offline = if (story.story.storyMetadata.isOffline) 1 else 0,
                language = story.story.storyMetadata.language
            )
        )
    }
}
