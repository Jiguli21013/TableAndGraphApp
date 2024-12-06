package com.yanchelenko.tableandgraphapp.ui.home

import android.text.Editable
import androidx.lifecycle.viewModelScope
import com.yanchelenko.tableandgraphapp.ui.base.BaseViewModel
import com.yanchelenko.tableandgraphapp.domain.usecase.points.GetPointsUseCase
import com.yanchelenko.tableandgraphapp.ui.UIAction
import com.yanchelenko.tableandgraphapp.ui.UIState
import com.yanchelenko.tableandgraphapp.ui.models.PointUI
import com.yanchelenko.tableandgraphapp.utils.navigation.SCREENS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
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

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: GetPointsUseCase
) : BaseViewModel() {

    private val _countPoints = MutableStateFlow<String>(value = "")
    val countPoints: StateFlow<String> = _countPoints

    fun newCountValue(newValue: Editable) {
        _countPoints.value = newValue.toString() ?: ""
    }

    //todo где хранить uiState?
    private val _uiState = MutableSharedFlow<UIState>()
    val uiState = _uiState.asSharedFlow()

    val isUiLoading = _uiState.map { it == UIState.Loading }.stateIn(
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
                        /* todo request не могу придумать как лучше передавать данные в TableFragment))))
                        setState(newUIState = useCase.execute(count = action.count).toState().also {
                            points = (it as UIState.Success).points
                        })

                         */
                        //mock request
                        delay(300L)
                        setState(newUIState = UIState.Success(points = listOf<PointUI>().toImmutableList()))
                    }
                }
                is UIAction.NAVIGATE -> {
                    navigateToScreen(screen = SCREENS.TABLE)
                }
            }
        }
    }

    private suspend fun setState(newUIState: UIState) {
        println("---setState---${newUIState}")
        _uiState.emit(newUIState)
    }

    fun onBtnSendClicked() {
        val count = countPoints.value.toIntOrNull() ?: return
        onAction(action = UIAction.SEND(count = count))
    }
}
