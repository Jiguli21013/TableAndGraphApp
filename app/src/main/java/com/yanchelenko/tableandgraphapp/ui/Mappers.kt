package com.yanchelenko.tableandgraphapp.ui

import com.yanchelenko.tableandgraphapp.data.RequestResult
import com.yanchelenko.tableandgraphapp.ui.models.ListOfPointsUI

fun RequestResult<ListOfPointsUI>.toState(): UIState {
    return when (this) {
        is RequestResult.Error -> UIState.Error(message = this.error?.message ?: "")
        is RequestResult.InProgress -> UIState.Loading
        is RequestResult.Success -> UIState.Success(data)
    }
}
