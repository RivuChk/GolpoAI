package dev.rivu.golpoai.presentation

data class StoryUiState(
    val prompt: String = "",
    val genre: String = "",
    val story: String? = null,
    val loading: Boolean = false,
    val error: String? = null,
    val saved: Boolean = false,
)
