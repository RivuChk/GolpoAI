package dev.rivu.golpoai.domain

import dev.rivu.golpoai.data.repositories.StoryRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow


class StoryUseCaseTest : BehaviorSpec({

    val mockRepository = mockk<StoryRepository>()

    val isReadyFlow = MutableStateFlow(true)

    // Mock the val property
    every { mockRepository.isOfflineModelReady } returns isReadyFlow

    val useCase = StoryUseCase(mockRepository)

    Given("a valid prompt, genre and language") {
        val prompt = "An astronaut lost on Mars"
        val genre = "Sci-fi"
        val language = "English"
        val expectedStory = Result.success("Once upon a time... on Mars")

        coEvery { mockRepository.generateStory(any(), any()) } returns expectedStory

        When("generateStory is called with offline=false") {
            val result = useCase.generateStory(prompt, genre, language, offline = false)

            Then("it should return the generated story using online model") {
                result shouldBe expectedStory
            }
        }

        When("generateStory is called with offline=true") {
            val result = useCase.generateStory(prompt, genre, language, offline = true)

            Then("it should return the generated story using offline model") {
                result shouldBe expectedStory
            }
        }
    }
})
