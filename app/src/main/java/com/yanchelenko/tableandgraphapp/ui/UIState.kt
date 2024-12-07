package com.yanchelenko.tableandgraphapp.ui

import com.yanchelenko.tableandgraphapp.ui.models.ListOfPointsUI
import com.yanchelenko.tableandgraphapp.ui.models.PointUI
import kotlinx.collections.immutable.ImmutableList

sealed class UIState {

    data object None : UIState()
    data object Loading : UIState()

    class Error(val message: String) : UIState() //todo error msg ///val message: String
    class Success(val points: ListOfPointsUI) : UIState()

}
