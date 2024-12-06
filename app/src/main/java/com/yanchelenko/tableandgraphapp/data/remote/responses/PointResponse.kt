package com.yanchelenko.tableandgraphapp.data.remote.responses

import com.google.gson.annotations.SerializedName
import com.yanchelenko.tableandgraphapp.domain.entity.Point

data class PointResponse(
    @SerializedName ("x") val x: Float?,
    @SerializedName ("y") val y: Float?
)

fun PointResponse.toEntity() = Point(
    x = x ?: 0f,
    y = y ?: 0f, //todo
)
