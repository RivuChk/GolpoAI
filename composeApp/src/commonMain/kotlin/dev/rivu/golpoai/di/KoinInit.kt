package dev.rivu.golpoai.di

import composeAppModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        sharedModule,
        composeAppModule,
        databaseModule,
        sqlDriverModule
    )
}

fun initKoin() = initKoin { }
