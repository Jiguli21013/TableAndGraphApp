package com.yanchelenko.tableandgraphapp.domain.usecase.points

import com.yanchelenko.tableandgraphapp.data.RequestResult
import com.yanchelenko.tableandgraphapp.domain.repository.PointsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.yanchelenko.tableandgraphapp.data.map
import com.yanchelenko.tableandgraphapp.data.toRequestResult
import com.yanchelenko.tableandgraphapp.ui.models.ListOfPointsUI
import com.yanchelenko.tableandgraphapp.ui.models.PointUI
import com.yanchelenko.tableandgraphapp.ui.models.toUiPoints
import com.yanchelenko.tableandgraphapp.ui.models.toUiPoint


class GetPointsUseCase(
    private val repository: PointsRepository
) {
    operator fun invoke(count: Int): Flow<RequestResult<ListOfPointsUI>> {
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
                        .toUiPoints()
                }
            }
    }

    suspend fun execute(count: Int): RequestResult<ListOfPointsUI> {

        //todo
        /*
        repository.doRequest2(count = count)
            .map { points ->
                points
                    .sortedBy { point ->
                        point.x
                    }
                    .map { point ->
                        point.toUiPoint()
                    }
                    .toUiPoints()
            }
         */

        return Result.success(generateData().toUiPoints()).toRequestResult()
    }

    //todo delete
    private fun generateData(): List<PointUI> {
        val min = -100f
        val max = 100f
        val points = mutableListOf<PointUI>()

        // Генерация 20 случайных точек
        for (i in 1..20) {
            val x = min + Math.random() * (max - min)  // Случайное значение x от -100 до 100
            val y = min + Math.random() * (max - min)  // Случайное значение y от -100 до 100
            points.add(PointUI(x.toFloat(), y.toFloat()))
        }

        val newPoints = points.sortedBy { it.x }
        return newPoints
    }
}
