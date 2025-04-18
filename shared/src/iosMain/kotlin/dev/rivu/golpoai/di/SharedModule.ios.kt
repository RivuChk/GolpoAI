package dev.rivu.golpoai.di

import dev.rivu.genai.kmpllminterferencelib.TextGenerator
import org.koin.core.module.Module
import org.koin.dsl.module

actual val TextGeneratorModule: Module = module {
    single { TextGenerator() }
}
