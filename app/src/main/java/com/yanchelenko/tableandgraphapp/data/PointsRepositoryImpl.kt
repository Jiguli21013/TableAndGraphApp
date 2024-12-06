package com.yanchelenko.tableandgraphapp.data

import com.yanchelenko.tableandgraphapp.data.remote.responses.toEntity
import com.yanchelenko.tableandgraphapp.data.remote.services.points.PointService
import com.yanchelenko.tableandgraphapp.domain.entity.PointEntity
import com.yanchelenko.tableandgraphapp.domain.repository.PointsRepository
import com.yanchelenko.tableandgraphapp.ui.models.PointUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

class PointsRepositoryImpl(
    private val service: PointService
): PointsRepository {
    override fun doRequest(count: Int): Flow<RequestResult<List<PointEntity>>> {
        val inProgress = flowOf<RequestResult<List<PointEntity>>>(RequestResult.InProgress(data = null))

        val apiRequest = flow { emit (service.getListOfPoints(count = count)) }
            .map { result ->
                println("---rep--result---isSuccess---${result.isSuccess}")
                result.toRequestResult()
                    .map { it.points.map { pointResponse -> pointResponse.toEntity() } }

            }

        return merge(apiRequest, inProgress)
    }

    override suspend fun doRequest2(count: Int): RequestResult<List<PointEntity>> {
        return service.getListOfPoints(count = count)
            .toRequestResult()
            .map { response ->
                response.points.map { it.toEntity() }
            }
    }
}
