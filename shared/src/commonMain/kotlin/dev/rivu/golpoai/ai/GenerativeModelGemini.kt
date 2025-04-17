package dev.rivu.golpoai.ai

import dev.shreyaspatil.ai.client.generativeai.type.content
import dev.shreyaspatil.ai.client.generativeai.GenerativeModel as GeminiApiGenerativeModel

class GenerativeModelGemini(private val apiKey: String) : GenerativeModel {
    private val model = GeminiApiGenerativeModel(
        modelName = "googleai/gemini-2.0-flash",
        apiKey = apiKey
    )

    override suspend fun generateStory(prompt: String): String {
        val input = content { text(prompt) }
        val response = model.generateContent(input)
        return response.text ?: "No story generated."
    }
}
