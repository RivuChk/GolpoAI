package dev.rivu.golpoai.ai

enum class GenerationType(val displayName: String) {
    LOCAL("Local"),
    OLLAMA("Finetuned Ollama"),
    ONLINE_GEMINI("Online Gemini")
}