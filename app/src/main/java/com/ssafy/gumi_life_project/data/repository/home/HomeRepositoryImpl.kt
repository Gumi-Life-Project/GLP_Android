package com.ssafy.gumi_life_project.data.repository.home

import com.ssafy.gumi_life_project.data.remote.ApiService
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : HomeRepository {

}