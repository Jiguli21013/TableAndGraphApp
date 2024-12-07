package com.yanchelenko.tableandgraphapp.ui.models

import java.io.Serializable

data class ListOfPointsUI(
    val points: List<PointUI>
) : Serializable //todo Parcelable ?

fun List<PointUI>.toUiPoints(): ListOfPointsUI {
    return ListOfPointsUI(points = this)
}
