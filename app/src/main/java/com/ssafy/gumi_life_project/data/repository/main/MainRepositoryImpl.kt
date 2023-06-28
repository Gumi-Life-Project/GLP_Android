package com.ssafy.gumi_life_project.data.repository.main

import com.ssafy.gumi_life_project.data.remote.ApiService
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MainRepository {

}
