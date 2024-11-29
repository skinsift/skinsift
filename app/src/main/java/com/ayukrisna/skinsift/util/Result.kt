package com.ayukrisna.skinsift.util

sealed class Result<out R> private constructor() {
    object Idle : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}