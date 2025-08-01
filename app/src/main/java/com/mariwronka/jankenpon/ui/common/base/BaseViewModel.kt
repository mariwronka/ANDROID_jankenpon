package com.mariwronka.jankenpon.ui.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariwronka.jankenpon.di.IO_DISPATCHER
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named

abstract class BaseViewModel<T>(
    private val backgroundDispatcher: CoroutineDispatcher? = null,
) : ViewModel(), KoinComponent {

    private val _uiState = MutableStateFlow<BaseUiState<T>>(BaseUiState.Idle)
    val baseUiState: StateFlow<BaseUiState<T>> = _uiState.asStateFlow()

    fun launchDataLoad(showLoading: Boolean = true, block: suspend () -> T) {
        viewModelScope.launch(backgroundDispatcher ?: get<CoroutineDispatcher>(named(IO_DISPATCHER))) {
            if (showLoading) _uiState.value = BaseUiState.Loading
            runCatching { block() }
                .onSuccess { result -> _uiState.value = BaseUiState.Success(result) }
                .onFailure { throwable -> _uiState.value = BaseUiState.Error(throwable) }
        }
    }

    fun postIdle() {
        _uiState.value = BaseUiState.Idle
    }
}
