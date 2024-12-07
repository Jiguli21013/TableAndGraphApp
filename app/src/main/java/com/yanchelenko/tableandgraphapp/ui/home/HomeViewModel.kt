package com.yanchelenko.tableandgraphapp.ui.home

import android.text.Editable
import androidx.lifecycle.viewModelScope
import com.yanchelenko.tableandgraphapp.ui.base.BaseViewModel
import com.yanchelenko.tableandgraphapp.domain.usecase.points.GetPointsUseCase
import com.yanchelenko.tableandgraphapp.ui.UIAction
import com.yanchelenko.tableandgraphapp.ui.UIState
import com.yanchelenko.tableandgraphapp.utils.navigation.SCREENS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.yanchelenko.tableandgraphapp.ui.toState

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: GetPointsUseCase
) : BaseViewModel() {

    private val _countPoints = MutableStateFlow<String>(value = "")
    val countPoints: StateFlow<String> = _countPoints

    fun newCountValue(newValue: Editable) {
        _countPoints.value = newValue.toString()
    }

    val isUiLoading = uiState.map { it == UIState.Loading }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = false
    )

    fun onAction(action: UIAction) {
        println("---onAction")
        viewModelScope.launch {
            when (action) {
                is UIAction.SEND -> {
                    setState(newUIState = UIState.Loading)
                    withContext(Dispatchers.IO) {
                        delay(300L)    //todo
                        setState(newUIState = useCase.execute(count = action.count).toState())
                    }
                }
                is UIAction.NAVIGATE -> {
                    navigateToScreen(screen = SCREENS.TABLE.apply {
                        navDirections = HomeFragmentDirections
                            .actionHomeFragmentToTableFragment(action.points)
                    })
                }
                else -> {}
            }
        }
    }


    //todo или сразу вызывать onAction в xml?
    fun onBtnSendClicked() {
        val count = countPoints.value.toIntOrNull() ?: return
        onAction(action = UIAction.SEND(count = count))
    }
}
