package com.yanchelenko.tableandgraphapp.ui.table

import androidx.lifecycle.viewModelScope
import com.yanchelenko.tableandgraphapp.ui.UIAction
import com.yanchelenko.tableandgraphapp.ui.UIState
import com.yanchelenko.tableandgraphapp.ui.base.BaseViewModel
import com.yanchelenko.tableandgraphapp.ui.toState
import com.yanchelenko.tableandgraphapp.utils.navigation.SCREENS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TableViewModel @Inject constructor(

) : BaseViewModel() {
    //todo или сразу вызывать onAction в xml?
    fun onBtnSaveFileClicked() = onAction(action = UIAction.SAVE_FILE)

    fun onAction(action: UIAction) {
        println("---onAction")
        viewModelScope.launch {
            when (action) {
                is UIAction.SAVE_FILE -> {
                    //todo хм, а дальше то как, мне надо вью как-то прокидывать в функцию...
                    //setState(newUIState = UIState.Success(data = null))
                }
                else -> {}
            }
        }
    }
}
