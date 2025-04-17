package dev.rivu.golpoai.data

import dev.shreyaspatil.ai.client.generativeai.GenerativeModel
import dev.shreyaspatil.ai.client.generativeai.type.content

class StoryRepository(private val apiKey: String) {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-pro",
        apiKey = apiKey
    )

    suspend fun getStory(prompt: String): String {
        val inputContent = content {
            text(prompt)
        }
        val response = generativeModel.generateContent(inputContent)
        return response.text ?: "No story generated."
    }
}
