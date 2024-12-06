package com.yanchelenko.tableandgraphapp.di

import com.yanchelenko.tableandgraphapp.data.PointsRepositoryImpl
import com.yanchelenko.tableandgraphapp.data.remote.services.points.PointService
import com.yanchelenko.tableandgraphapp.domain.repository.PointsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideAuthRepository(service: PointService): PointsRepository = PointsRepositoryImpl(service)
}