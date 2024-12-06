package com.yanchelenko.tableandgraphapp.data.remote.api

import com.yanchelenko.tableandgraphapp.data.remote.responses.PointsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PointsApi {
    @GET("points")
    suspend fun getListOfPoints(@Query("count") count: Int): Response<PointsResponse>
}
