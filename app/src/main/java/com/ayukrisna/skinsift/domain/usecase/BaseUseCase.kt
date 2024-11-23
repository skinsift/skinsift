package com.ayukrisna.skinsift.domain.usecase

abstract class BaseUseCase<Input, Output> {
    abstract fun execute(input: Input): Output
}