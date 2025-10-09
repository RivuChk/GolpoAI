package dev.rivu.golpoai.ui

// Local copy to avoid import issues - TODO: fix module dependencies
enum class GenerationType(val displayName: String) {
    LOCAL("Local"),
    OLLAMA("Finetuned Ollama"),
    ONLINE_GEMINI("Online Gemini")
}