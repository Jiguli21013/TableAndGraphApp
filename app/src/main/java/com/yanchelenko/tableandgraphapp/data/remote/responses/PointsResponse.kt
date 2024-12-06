package com.yanchelenko.tableandgraphapp.data.remote.responses

import com.google.gson.annotations.SerializedName

data class PointsResponse(
    @SerializedName("points") val points: List<PointResponse>
)
