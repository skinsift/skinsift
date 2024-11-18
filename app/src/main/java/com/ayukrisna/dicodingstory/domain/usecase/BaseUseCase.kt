package com.ayukrisna.dicodingstory.domain.usecase

abstract class BaseUseCase<Input, Output> {
    abstract fun execute(input: Input): Output
}