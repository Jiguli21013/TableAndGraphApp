package com.yanchelenko.tableandgraphapp.di.usecases

import com.yanchelenko.tableandgraphapp.data.PointsRepositoryImpl
import com.yanchelenko.tableandgraphapp.domain.entity.Point
import com.yanchelenko.tableandgraphapp.domain.repository.PointsRepository
import com.yanchelenko.tableandgraphapp.domain.usecase.points.GetPointsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PointsUseCaseModule {
    @Provides
    fun provideGetPointsUseCase(PointsRepository: PointsRepository) =
        GetPointsUseCase(PointsRepository)
}
