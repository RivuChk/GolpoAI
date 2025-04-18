package dev.rivu.golpoai.data

import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import dev.rivu.golpoai.data.models.SavedStory
import dev.rivu.golpoai.data.repositories.StoryHistoryRepository
import dev.rivu.golpoai.db.GolpoDatabaseQueries
import dev.rivu.golpoai.db.Story_history
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import jdk.jfr.internal.OldObjectSample.emit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class StoryHistoryRepositoryTest : BehaviorSpec({
    Given("a StoryHistoryRepository with mock queries") {
        val mockQueries = mockk<GolpoDatabaseQueries>(relaxed = true)
        val repository = StoryHistoryRepository(mockQueries)

        When("insertStory is called") {
            val story = SavedStory("1", "prompt", "genre", "story", 100L)

            Then("it should call insert on queries") {
                runTest {
                    repository.insertStory(story)
                    verify {
                        mockQueries.insertStory(
                            id = story.id,
                            prompt = story.prompt,
                            genre = story.genre,
                            story = story.story,
                            created_at = story.createdAt
                        )
                    }
                }
            }
        }

        /*When("getAllStories is called") {
            Then("it should emit the mapped SavedStory list") {
                runTest {
                    // Fake DB row
                    val fakeDbRow = Story_history("1", "prompt", "genre", "story", 123L)
                    val mockQuery = mockk<Query<Story_history>>(relaxed = true)

                    every { mockQuery.executeAsList() } returns listOf(fakeDbRow)
                    every { mockQueries.selectAll() } returns mockQuery

                    // Mock the SQLDelight mapToList extension
                    mockkStatic("app.cash.sqldelight.coroutines.FlowExtensionsKt")
                    every {
                        mockQuery.asFlow()
                            .mapToList(context = Dispatchers.Default)
                    } returns flowOf(listOf(fakeDbRow))

                    val result = repository.getAllStories().first()

                    result shouldBe listOf(
                        SavedStory("1", "prompt", "genre", "story", 123L)
                    )

                    unmockkStatic("app.cash.sqldelight.coroutines.FlowExtensionsKt")
                }
            }
        }*/
    }
})

