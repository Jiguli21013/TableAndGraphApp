package com.yanchelenko.tableandgraphapp.domain.repository

import com.yanchelenko.tableandgraphapp.domain.entity.Point

interface PointsRepository {
    suspend fun doRequest(count: Int): Result<List<Point>>
}
