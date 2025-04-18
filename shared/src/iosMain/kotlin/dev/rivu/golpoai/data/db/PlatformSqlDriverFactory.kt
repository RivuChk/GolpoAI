package dev.rivu.golpoai.data.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import dev.rivu.golpoai.db.GolpoDatabase

actual class PlatformSqlDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(GolpoDatabase.Schema, "golpo.db")
    }
}
