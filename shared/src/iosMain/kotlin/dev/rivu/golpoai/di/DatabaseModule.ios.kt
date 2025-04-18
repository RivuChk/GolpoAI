package dev.rivu.golpoai.di

import app.cash.sqldelight.db.SqlDriver
import dev.rivu.golpoai.data.db.PlatformSqlDriverFactory
import dev.rivu.golpoai.db.GolpoDatabase
import org.koin.core.module.Module
import org.koin.dsl.module


actual val sqlDriverModule: Module = module {
    single<SqlDriver> {
        PlatformSqlDriverFactory().create()
    }
}
