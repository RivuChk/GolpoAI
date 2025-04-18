package dev.rivu.golpoai.data.db

import app.cash.sqldelight.db.SqlDriver

expect class PlatformSqlDriverFactory {
    fun create(): SqlDriver
}
