package com.yanchelenko.tableandgraphapp.data.remote.services.points

import com.yanchelenko.tableandgraphapp.data.remote.responses.PointResponse

interface PointService {
    suspend fun getListOfPoints(count: Int): Result<List<PointResponse>>
}
