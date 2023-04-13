package com.sapient.demoapp.domain.util

sealed class Resource<out T : Any> {
    data class OnSuccess<out T : Any>(val data: T) : Resource<T>()
    data class OnFailure(val throwable: Throwable) : Resource<Nothing>()
}
