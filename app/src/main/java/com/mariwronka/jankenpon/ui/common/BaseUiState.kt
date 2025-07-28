package com.mariwronka.jankenpon.ui.common

sealed class BaseUiState<out T> {
    data object Idle : BaseUiState<Nothing>()
    data object Loading : BaseUiState<Nothing>()
    data class Success<T>(val data: T) : BaseUiState<T>()
    data class Error(val throwable: Throwable) : BaseUiState<Nothing>()
}
