package ru.diploma.appcomponents.core

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

private const val SAVED_UI_STATE_KEY = "savedUiStateKey"

abstract class BaseViewModel<UI_STATE : Parcelable, PARTIAL_UI_STATE : Parcelable, EVENT, INTENT>(
    savedStateHandle: SavedStateHandle,
    initialState: UI_STATE
) : ViewModel() {

    private val intentFlow = MutableSharedFlow<INTENT>()

    val uiState = savedStateHandle.getStateFlow(SAVED_UI_STATE_KEY, initialState)

    private val eventChannel = Channel<EVENT>(Channel.BUFFERED)
    val event = eventChannel.receiveAsFlow()

    init{
        viewModelScope.launch {
            intentFlow
                .flatMapMerge {mapIntents(it)}
                .scan(uiState.value, ::reduceUiState)
                .catch { Timber.e(it) }
                .collect{
                    savedStateHandle[SAVED_UI_STATE_KEY] = it
                }
        }
    }

    fun acceptIntent(intent: INTENT){
        viewModelScope.launch {
            intentFlow.emit(intent)
        }
    }

    protected fun publishEvent(event: EVENT){
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    protected abstract fun mapIntents(intent: INTENT): Flow<PARTIAL_UI_STATE>

    protected abstract fun reduceUiState(
        previousState: UI_STATE,
        partialUiState: PARTIAL_UI_STATE
    ): UI_STATE
}