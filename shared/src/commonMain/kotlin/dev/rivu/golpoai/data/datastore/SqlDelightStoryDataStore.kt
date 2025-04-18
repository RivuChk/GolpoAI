package dev.rivu.golpoai.data.datastore

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import dev.rivu.golpoai.db.GolpoDatabaseQueries
import dev.rivu.golpoai.db.Story_history
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class SqlDelightStoryDataStore(
    private val queries: GolpoDatabaseQueries
) : LocalStoryDataStore {

    override fun getAllStories(): Flow<List<Story_history>> =
        queries.selectAll()
            .asFlow()
            .mapToList(context = Dispatchers.Default)

    override fun insertStory(story: Story_history) {
        queries.insertStory(
            id = story.id,
            prompt = story.prompt,
            genre = story.genre,
            story = story.story,
            created_at = story.created_at
        )
    }
}
