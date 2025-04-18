package dev.rivu.golpoai.di

import dev.rivu.golpoai.ai.GenerativeModel
import dev.rivu.golpoai.ai.GenerativeModelGemini
import dev.rivu.golpoai.ai.LocalGenerativeModel
import dev.rivu.golpoai.config.BuildKonfig
import dev.rivu.golpoai.data.repositories.StoryRepository
import dev.rivu.golpoai.domain.HistoryUseCase
import dev.rivu.golpoai.domain.SaveStoryUseCase
import dev.rivu.golpoai.domain.StoryUseCase
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val sharedModule = module {
    single<GenerativeModel>(named("geminiApi")) { GenerativeModelGemini(BuildKonfig.GEMINI_API_KEY) }
    single {
        StoryRepository(
            onlineModel = get(named("geminiApi")),
            offlineModel = get(named("gemma"))
        )
    }
    single { StoryUseCase(get()) }
    single { HistoryUseCase(get()) }
    single { SaveStoryUseCase(get()) }
    single<GenerativeModel>(named("gemma")) { LocalGenerativeModel(get()) }
}

expect val TextGeneratorModule: Module
