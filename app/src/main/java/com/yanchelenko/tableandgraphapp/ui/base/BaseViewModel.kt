package com.yanchelenko.tableandgraphapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yanchelenko.tableandgraphapp.ui.UIState
import com.yanchelenko.tableandgraphapp.utils.navigation.SCREENS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _uiState = MutableSharedFlow<UIState>()
    val uiState = _uiState.asSharedFlow()

    suspend fun setState(newUIState: UIState) {
        println("---setState---${newUIState}")
        _uiState.emit(newUIState)
    }

    private val screenChannel = Channel<SCREENS>()
    val screenFlow = screenChannel.receiveAsFlow()

    protected fun navigateToScreen(screen: SCREENS) {
        viewModelScope.launch(Dispatchers.Main) {
            screenChannel.send(screen)
        }
    }

}
