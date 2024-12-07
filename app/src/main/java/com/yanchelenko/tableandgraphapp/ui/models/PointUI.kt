package com.yanchelenko.tableandgraphapp.ui.models

import com.yanchelenko.tableandgraphapp.domain.entity.PointEntity

data class PointUI(
    val x: Float,
    val y: Float
)

fun PointEntity.toUiPoint(): PointUI {
    return PointUI(
        x = x,
        y = y
    )
}
