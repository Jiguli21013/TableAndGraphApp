package com.yanchelenko.tableandgraphapp.domain.usecase.points

import com.yanchelenko.tableandgraphapp.data.RequestResult
import com.yanchelenko.tableandgraphapp.domain.repository.PointsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.yanchelenko.tableandgraphapp.data.map
import com.yanchelenko.tableandgraphapp.ui.models.PointUI
import com.yanchelenko.tableandgraphapp.ui.toUiPoint


class GetPointsUseCase(
    private val repository: PointsRepository
) {
    operator fun invoke(count: Int): Flow<RequestResult<List<PointUI>>> {
        return repository.doRequest(count = count)
            .map { requestResult ->
                requestResult.map { points ->
                    points
                        .sortedBy { point ->
                            point.x
                        }
                        .map { point ->
                            point.toUiPoint()
                        }
                }
            }
    }

    suspend fun execute(count: Int): RequestResult<List<PointUI>> {
        return repository.doRequest2(count = count)
            .map { points ->
                points
                    .sortedBy { point ->
                        point.x
                    }
                    .map { point ->
                        point.toUiPoint()
                    }
            }
    }
}
