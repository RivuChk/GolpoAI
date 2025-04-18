package dev.rivu.golpoai.di

import app.cash.sqldelight.db.SqlDriver
import dev.rivu.golpoai.data.db.PlatformSqlDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module


actual val sqlDriverModule: Module = module {
    single<SqlDriver> {
        PlatformSqlDriverFactory(androidContext()).create()
    }
}
