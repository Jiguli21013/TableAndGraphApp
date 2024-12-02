package com.yanchelenko.tableandgraphapp.data.remote.responses

import com.google.gson.annotations.SerializedName
import com.yanchelenko.tableandgraphapp.domain.entity.Point

data class PointResponse(
    @SerializedName ("x") val x: Int?,
    @SerializedName ("y") val y: Int?
)

fun PointResponse.toEntity() = Point(
    x = x ?: 0,
    y = y ?: 0, //todo
)
