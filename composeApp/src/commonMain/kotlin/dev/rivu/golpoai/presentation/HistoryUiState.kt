package dev.rivu.golpoai.presentation

import dev.rivu.golpoai.data.models.SavedStory

data class HistoryUiState(
    val stories: List<SavedStory> = emptyList()
)
