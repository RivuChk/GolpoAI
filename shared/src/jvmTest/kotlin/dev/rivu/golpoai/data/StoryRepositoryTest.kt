package dev.rivu.golpoai.data

import dev.rivu.golpoai.ai.GenerativeModel
import dev.rivu.golpoai.data.repositories.StoryRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.MutableStateFlow

class StoryRepositoryTest : BehaviorSpec({
    val isReady = MutableStateFlow(true)

    val onlineModel = object : GenerativeModel {
        override val isReady = isReady
        override suspend fun generateStory(prompt: String, awaitReadiness: Boolean): Result<String> {
            return Result.success("Online story for: $prompt")
        }
    }

    val offlineModel = object : GenerativeModel {
        override val isReady = isReady
        override suspend fun generateStory(prompt: String, awaitReadiness: Boolean): Result<String> {
            return Result.success("Offline story for: $prompt")
        }
    }

    val repository = StoryRepository(onlineModel, offlineModel)

    Given("a prompt for online mode") {
        val prompt = "Haunted village"
        val expected = Result.success("Online story for: $prompt")

        When("generateStory is called with offline=false") {
            val result = repository.generateStory(prompt, offline = false)

            Then("it should return the story from online model") {
                result shouldBe expected
            }
        }
    }

    Given("a prompt for offline mode") {
        val prompt = "Haunted village"
        val expected = Result.success("Offline story for: $prompt")

        When("generateStory is called with offline=true") {
            val result = repository.generateStory(prompt, offline = true)

            Then("it should return the story from offline model") {
                result shouldBe expected
            }
        }
    }

    Given("checking offline model readiness") {
        Then("isOfflineModelReady should reflect the model state") {
            repository.isOfflineModelReady.value shouldBe true
        }
    }
})
