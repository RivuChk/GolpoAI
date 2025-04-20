package dev.rivu.golpoai.presentation

import dev.rivu.golpoai.data.models.Story

data class StoryUiState(
    val story: Story? = null,
    val loading: Boolean = false,
    val error: String? = null,
    val saved: Boolean = false,
)
