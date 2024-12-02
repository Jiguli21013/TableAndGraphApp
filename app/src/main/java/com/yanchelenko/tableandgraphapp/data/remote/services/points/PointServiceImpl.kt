package com.yanchelenko.tableandgraphapp.data.remote.services.points

import com.yanchelenko.tableandgraphapp.data.remote.api.PointsApi
import com.yanchelenko.tableandgraphapp.data.remote.responses.PointResponse
import com.yanchelenko.tableandgraphapp.data.remote.services.BaseService

class PointServiceImpl(
    private val api: PointsApi
): PointService, BaseService() {
    override suspend fun getListOfPoints(count: Int): Result<List<PointResponse>> = apiCall {
        api.getListOfPoints(
            count = count
        )
    }
}