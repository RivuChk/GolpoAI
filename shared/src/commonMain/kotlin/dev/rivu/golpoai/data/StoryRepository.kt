package dev.rivu.golpoai.data

import dev.rivu.golpoai.ai.GenerativeModel


class StoryRepository(private val model: GenerativeModel) {


    suspend fun getStory(prompt: String): String {
        return model.generateStory(prompt)
    }
}
