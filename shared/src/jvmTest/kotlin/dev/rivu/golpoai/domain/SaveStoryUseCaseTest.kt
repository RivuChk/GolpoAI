package dev.rivu.golpoai.domain

import dev.rivu.golpoai.data.models.SavedStory
import dev.rivu.golpoai.data.repositories.StoryHistoryRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class SaveStoryUseCaseTest : BehaviorSpec({
    Given("a SaveStoryUseCase with a mock repository") {
        val mockRepository = mockk<StoryHistoryRepository>(relaxed = true)
        val useCase = SaveStoryUseCase(mockRepository)

        When("save is called with a story") {
            val story = SavedStory("id", "prompt", "genre", "story", 123L)

            Then("it should call insertStory on repository") {
                runTest {
                    useCase.save(story)
                    coVerify { mockRepository.insertStory(story) }
                }
            }
        }
    }
})
