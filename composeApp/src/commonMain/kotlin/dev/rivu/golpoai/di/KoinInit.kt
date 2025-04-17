package dev.rivu.golpoai.di

import composeAppModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.mp.KoinPlatform.startKoin

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(sharedModule, composeAppModule)
}

fun initKoin() = initKoin { }
