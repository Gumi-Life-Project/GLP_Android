package com.ssafy.gumi_life_project.di.module

import com.ssafy.gumi_life_project.data.repository.board.BoardRepository
import com.ssafy.gumi_life_project.data.repository.board.BoardRepositoryImpl
import com.ssafy.gumi_life_project.data.repository.home.HomeRepository
import com.ssafy.gumi_life_project.data.repository.home.HomeRepositoryImpl
import com.ssafy.gumi_life_project.data.repository.main.MainRepository
import com.ssafy.gumi_life_project.data.repository.main.MainRepositoryImpl
import com.ssafy.gumi_life_project.data.repository.user.UserRepository
import com.ssafy.gumi_life_project.data.repository.user.UserRepositoryImpl
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

    @Binds
    abstract fun bindsBoardRepository(
        repositoryImpl: BoardRepositoryImpl
    ): BoardRepository

    @Binds
    abstract fun bindsUserRepository(
        repositoryImpl: UserRepositoryImpl
    ): UserRepository
}