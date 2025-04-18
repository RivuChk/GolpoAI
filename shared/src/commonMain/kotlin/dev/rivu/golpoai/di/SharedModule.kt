package dev.rivu.golpoai.di

import dev.rivu.golpoai.ai.GenerativeModel
import dev.rivu.golpoai.ai.GenerativeModelGemini
import dev.rivu.golpoai.config.BuildKonfig
import dev.rivu.golpoai.data.repositories.StoryRepository
import dev.rivu.golpoai.domain.HistoryUseCase
import dev.rivu.golpoai.domain.SaveStoryUseCase
import dev.rivu.golpoai.domain.StoryUseCase
import org.koin.dsl.module

val sharedModule = module {
    single<GenerativeModel> { GenerativeModelGemini(BuildKonfig.GEMINI_API_KEY) }
    single { StoryRepository(get()) }
    single { StoryUseCase(get()) }
    single { HistoryUseCase(get()) }
    single { SaveStoryUseCase(get()) }
}
