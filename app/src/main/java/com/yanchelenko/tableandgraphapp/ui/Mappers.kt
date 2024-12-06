package com.yanchelenko.tableandgraphapp.ui

import com.yanchelenko.tableandgraphapp.data.RequestResult
import com.yanchelenko.tableandgraphapp.domain.entity.Point
import com.yanchelenko.tableandgraphapp.ui.models.PointUI
import kotlinx.collections.immutable.toImmutableList

fun RequestResult<List<PointUI>>.toState(): UIState {
    return when (this) {
        is RequestResult.Error -> UIState.Error()
        is RequestResult.InProgress -> UIState.Loading
        is RequestResult.Success -> UIState.Success(data.toImmutableList())
    }
}

fun Point.toUiPoint(): PointUI {
    return PointUI(
        x = x,
        y = y
    )
}
