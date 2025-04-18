package dev.rivu.golpoai.domain

import dev.rivu.golpoai.data.models.SavedStory
import dev.rivu.golpoai.data.repositories.StoryHistoryRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class HistoryUseCaseTest : BehaviorSpec({
    Given("a HistoryUseCase with a mock repository") {
        val mockRepository = mockk<StoryHistoryRepository>()
        val useCase = HistoryUseCase(mockRepository)

        When("getAllStories is called") {
            val sampleList = listOf(SavedStory("1", "prompt", "genre", "story", 100L))
            coEvery { mockRepository.getAllStories() } returns flowOf(sampleList)

            Then("it should return the same list from repository") {
                runTest {
                    val result = useCase.getAllStories()
                    result.collect {
                        it shouldContainExactly sampleList
                    }
                }
            }
        }
    }
})
