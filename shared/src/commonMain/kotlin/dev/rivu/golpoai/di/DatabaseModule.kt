package dev.rivu.golpoai.di

import app.cash.sqldelight.db.SqlDriver
import dev.rivu.golpoai.data.repositories.StoryHistoryRepository
import dev.rivu.golpoai.db.GolpoDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule = module {
    single {
        GolpoDatabase(get()).golpoDatabaseQueries
    }
    single { StoryHistoryRepository(get()) }
}

expect val sqlDriverModule: Module
