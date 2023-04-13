package com.sapient.demoapp.presentation.viewState

sealed class UIState<out T : Any> {
    data class Loading(val isLoading: Boolean) : UIState<Nothing>()
    data class Success<out T : Any>(val output: T) : UIState<T>()
    data class Failure(val throwable: Throwable) : UIState<Nothing>()
}