package com.nerevar.nba.core

import com.nerevar.nba.core.random_image.RandomImageUseCase
import com.nerevar.nba.core.random_image.RandomImageUseCaseImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreRandomImageModule = module {
    singleOf(::RandomImageUseCaseImpl) bind RandomImageUseCase::class
}
