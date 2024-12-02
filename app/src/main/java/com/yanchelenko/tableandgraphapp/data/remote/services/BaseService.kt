package com.yanchelenko.tableandgraphapp.data.remote.services

import retrofit2.Response

abstract class BaseService {

    protected suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): Result<T> {
        val response: Response<T>
        try {
            response = call.invoke()
        } catch (e: Exception) {
            return Result.failure(Exception())
        }

        return if (!response.isSuccessful) {
            Result.failure(Exception())
        } else {
            if (response.body() == null) {
                Result.failure(Exception())
            } else {
                Result.success(response.body()!!)
            }
        }
    }
}
