package dev.rivu.golpoai.data.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dev.rivu.golpoai.db.GolpoDatabase

actual class PlatformSqlDriverFactory(private val context: Context) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(GolpoDatabase.Schema, context, "golpo.db")
    }
}
