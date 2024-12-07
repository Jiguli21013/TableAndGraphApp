package com.yanchelenko.tableandgraphapp.data.remote.responses

import com.google.gson.annotations.SerializedName
import com.yanchelenko.tableandgraphapp.domain.entity.PointEntity

data class PointResponse(
    @SerializedName ("x") val x: Float?,
    @SerializedName ("y") val y: Float?
)

fun PointResponse.toEntity() = PointEntity(
    x = x ?: 0f,
    y = y ?: 0f, //todo
)
