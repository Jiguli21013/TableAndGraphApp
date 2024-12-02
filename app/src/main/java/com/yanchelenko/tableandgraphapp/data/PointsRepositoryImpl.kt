package com.yanchelenko.tableandgraphapp.data

import com.yanchelenko.tableandgraphapp.data.remote.responses.toEntity
import com.yanchelenko.tableandgraphapp.data.remote.services.points.PointService
import com.yanchelenko.tableandgraphapp.domain.entity.Point
import com.yanchelenko.tableandgraphapp.domain.repository.PointsRepository

class PointsRepositoryImpl(
    private val service: PointService
): PointsRepository {
    override suspend fun doRequest(count: Int): Result<List<Point>> {
        return service.getListOfPoints(count = count).map { response ->
            response.map { pointResponse ->
                pointResponse.toEntity()
            }
        }
    }
}
