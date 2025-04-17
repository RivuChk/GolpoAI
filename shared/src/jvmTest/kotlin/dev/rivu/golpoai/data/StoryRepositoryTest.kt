package dev.rivu.golpoai.data

import dev.rivu.golpoai.ai.GenerativeModel
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class StoryRepositoryTest : BehaviorSpec({

    val fakeModel = object : GenerativeModel {
        override suspend fun generateStory(prompt: String): String {
            return "Fake story for: $prompt"
        }
    }

    val repository = StoryRepository(fakeModel)

    Given("a prompt") {
        val prompt = "Haunted village"
        val expected = "Fake story for: $prompt"

        When("getStory is called") {
            val result = repository.getStory(prompt)

            Then("it should return the fake story") {
                result shouldBe expected
            }
        }
    }
})
