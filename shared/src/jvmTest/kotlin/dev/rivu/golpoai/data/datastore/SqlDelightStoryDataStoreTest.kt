package dev.rivu.golpoai.data.datastore

import app.cash.sqldelight.Query
import dev.rivu.golpoai.db.GolpoDatabaseQueries
import dev.rivu.golpoai.db.Story_history
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest

class SqlDelightStoryDataStoreTest : BehaviorSpec({

    Given("a SqlDelightStoryDataStore with mock GolpoDatabaseQueries") {
        val mockQueries = mockk<GolpoDatabaseQueries>(relaxed = true)
        val dataStore = SqlDelightStoryDataStore(mockQueries)

        When("insertStory is called") {
            val story = Story_history("1", "prompt", "genre", "story", 123L)

            Then("it should delegate to GolpoDatabaseQueries.insertStory") {
                runTest {
                    dataStore.insertStory(story)
                    verify {
                        mockQueries.insertStory(
                            id = story.id,
                            prompt = story.prompt,
                            genre = story.genre,
                            story = story.story,
                            created_at = story.created_at
                        )
                    }
                }
            }
        }

    }
})
