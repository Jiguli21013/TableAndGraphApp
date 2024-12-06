package com.yanchelenko.tableandgraphapp.data.remote.services.points

import com.yanchelenko.tableandgraphapp.data.remote.api.PointsApi
import com.yanchelenko.tableandgraphapp.data.remote.responses.PointResponse
import com.yanchelenko.tableandgraphapp.data.remote.responses.PointsResponse
import com.yanchelenko.tableandgraphapp.data.remote.services.BaseService

class PointServiceImpl(
    private val api: PointsApi
): PointService, BaseService() {
    override suspend fun getListOfPoints(count: Int): Result<PointsResponse> = apiCall {
        api.getListOfPoints(count = count)
    }
}
