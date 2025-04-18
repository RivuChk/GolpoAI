package dev.rivu.golpoai.data.repositories

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import dev.rivu.golpoai.data.models.SavedStory
import dev.rivu.golpoai.db.GolpoDatabaseQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoryHistoryRepository(private val queries: GolpoDatabaseQueries) {

    fun getAllStories(): Flow<List<SavedStory>> =
        queries.selectAll()
            .asFlow()
            .mapToList(context = Dispatchers.Default) // ✅ pass context explicitly
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
        queries.insertStory(
            id = story.id,
            prompt = story.prompt,
            genre = story.genre,
            story = story.story,
            created_at = story.createdAt
        )
    }
}
