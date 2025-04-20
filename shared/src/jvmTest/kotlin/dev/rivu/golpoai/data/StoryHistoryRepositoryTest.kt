package dev.rivu.golpoai.data

import dev.rivu.golpoai.data.datastore.LocalStoryDataStore
import dev.rivu.golpoai.data.models.SavedStory
import dev.rivu.golpoai.data.models.Story
import dev.rivu.golpoai.data.models.StoryMetadata
import dev.rivu.golpoai.data.repositories.StoryHistoryRepository
import dev.rivu.golpoai.db.Story_history
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class StoryHistoryRepositoryTest : BehaviorSpec({

    Given("a StoryHistoryRepository with a mock LocalStoryDataStore") {
        val mockDataStore = mockk<LocalStoryDataStore>(relaxed = true)
        val repository = StoryHistoryRepository(mockDataStore)

        When("insertStory is called") {
            val story = SavedStory("1", Story( storyMetadata = StoryMetadata("title", "prompt", "genre", "story", false, 100L), "story"))

            Then("it should delegate to the data store") {
                runTest {
                    repository.insertStory(story)
                    verify {
                        mockDataStore.insertStory(
                            Story_history(
                                id = story.id,
                                prompt = story.story.storyMetadata.prompt,
                                genre = story.story.storyMetadata.genre,
                                story = story.story.storyText,
                                created_at = story.story.storyMetadata.createdAt,
                                language = story.story.storyMetadata.language,
                                is_offline = if (story.story.storyMetadata.isOffline) 1 else 0
                            )
                        )
                    }
                }
            }
        }

        When("getAllStories is called") {
            Then("it should map and return SavedStory list") {
                runTest {
                    val storyRow = Story_history("1", "prompt", "genre", "story", language = "English", is_offline = 0, created_at = 123L)
                    every { mockDataStore.getAllStories() } returns flowOf(listOf(storyRow))

                    val result = repository.getAllStories().first()

                    result shouldBe listOf(
                        SavedStory("1", Story( storyMetadata = StoryMetadata("", "prompt", "genre", "English", false, 123), "story"))
                    )
                }
            }
        }
    }
})

