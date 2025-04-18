package dev.rivu.golpoai.presentation

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.rivu.golpoai.data.models.SavedStory
import dev.rivu.golpoai.domain.HistoryUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class HistoryUiState(
    val stories: List<SavedStory> = emptyList()
)

class HistoryScreenModel(
    private val useCase: HistoryUseCase
) : StateScreenModel<HistoryUiState>(HistoryUiState()) {

    init {
        screenModelScope.launch {
            useCase.getAllStories().collectLatest {
                mutableState.value = HistoryUiState(stories = it)
            }
        }
    }
}
