package com.yanchelenko.tableandgraphapp.domain.repository

import com.yanchelenko.tableandgraphapp.data.RequestResult
import com.yanchelenko.tableandgraphapp.domain.entity.PointEntity
import kotlinx.coroutines.flow.Flow

interface PointsRepository {
    fun doRequest(count: Int): Flow<RequestResult<List<PointEntity>>>
    suspend fun doRequest2(count: Int): RequestResult<List<PointEntity>>
}
