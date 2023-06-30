package com.ssafy.gumi_life_project.di.module

import com.ssafy.gumi_life_project.data.repository.home.HomeRepository
import com.ssafy.gumi_life_project.data.repository.home.HomeRepositoryImpl
import com.ssafy.gumi_life_project.data.repository.main.MainRepository
import com.ssafy.gumi_life_project.data.repository.main.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsMainRepository(
        repositoryImpl: MainRepositoryImpl
    ): MainRepository

    @Binds
    abstract fun bindsHomeRepository(
        repositoryImpl: HomeRepositoryImpl
    ): HomeRepository
}