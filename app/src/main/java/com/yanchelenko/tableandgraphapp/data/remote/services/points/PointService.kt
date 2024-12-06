package com.yanchelenko.tableandgraphapp.data.remote.services.points

import com.yanchelenko.tableandgraphapp.data.remote.responses.PointsResponse

interface PointService {
    suspend fun getListOfPoints(count: Int): Result<PointsResponse>
}
