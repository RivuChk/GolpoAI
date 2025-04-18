package dev.rivu.golpoai.domain

import dev.rivu.golpoai.data.repositories.StoryRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk


class StoryUseCaseTest : BehaviorSpec({

    val mockRepository = mockk<StoryRepository>()
    val useCase = StoryUseCase(mockRepository)

    Given("a valid prompt, genre and language") {
        val prompt = "An astronaut lost on Mars"
        val genre = "Sci-fi"
        val language = "English"
        val expectedStory = Result.success("Once upon a time... on Mars")

        coEvery { mockRepository.getStory(any()) } returns expectedStory

        When("generateStory is called") {
            val result = useCase.generateStory(prompt, genre, language)

            Then("it should return the generated story") {
                result shouldBe expectedStory
            }
        }
    }
})
