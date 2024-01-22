package com.nerevar.nba.core.random_image

interface RandomImageUseCase {
    operator fun invoke(): String
}

internal class RandomImageUseCaseImpl : RandomImageUseCase {
    override fun invoke(): String = "https://picsum.photos/500"
}
