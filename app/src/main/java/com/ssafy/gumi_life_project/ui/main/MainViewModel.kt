package com.ssafy.gumi_life_project.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.ssafy.gumi_life_project.data.local.AppPreferences
import com.ssafy.gumi_life_project.data.model.*
import com.ssafy.gumi_life_project.data.repository.main.MainRepository
import com.ssafy.gumi_life_project.data.repository.user.UserRepository
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import com.ssafy.gumi_life_project.util.template.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> = _userId

    private val _tip = MutableLiveData<List<Tip>>()
    val tip: LiveData<List<Tip>> = _tip

    private val _weather = MutableLiveData<WeatherResponse>()
    val weather: LiveData<WeatherResponse> = _weather

    private val _shuttleBusStopMark = MutableLiveData<ShuttleBusStop>()
    val shuttleBusStopMark: LiveData<ShuttleBusStop> = _shuttleBusStopMark

    private val _meal = MutableLiveData<MealResponse>()
    val meal: LiveData<MealResponse> = _meal

    private val _kakaoUser = MutableLiveData<com.kakao.sdk.user.model.User>()
    val kakaoUser: LiveData<com.kakao.sdk.user.model.User> = _kakaoUser

    fun getAllTipList() {
        showProgress()
        viewModelScope.launch {
            val response = repository.getAllTipList()

            val type = "정보 조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    _tip.postValue(response.body)
                }

                is NetworkResponse.ApiError -> {
                    postValueEvent(0, type)
                }

                is NetworkResponse.NetworkError -> {
                    postValueEvent(1, type)
                }

                is NetworkResponse.UnknownError -> {
                    postValueEvent(2, type)
                }
            }
        }
        hideProgress()
    }

    fun findId() {
        showProgress()
        viewModelScope.launch {
            val response = repository.findId()

            val type = "id 정보 조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    _userId.postValue(response.body)
                }

                is NetworkResponse.ApiError -> {
                    postValueEvent(0, type)
                }

                is NetworkResponse.NetworkError -> {
                    postValueEvent(1, type)
                }

                is NetworkResponse.UnknownError -> {
                    postValueEvent(2, type)
                }
            }
        }
        hideProgress()
    }

    fun getMealList() {
        showProgress()
        viewModelScope.launch {
            val response = repository.getMealList()

            val type = "정보 조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    _meal.postValue(response.body)
                }

                is NetworkResponse.ApiError -> {
                    postValueEvent(0, type)
                }

                is NetworkResponse.NetworkError -> {
                    postValueEvent(1, type)
                }

                is NetworkResponse.UnknownError -> {
                    postValueEvent(2, type)
                }
            }

            hideProgress()
        }
    }

    fun getNowWeather() {
        showProgress()
        viewModelScope.launch {
            val response = repository.getNowWeather()
            val failResponses = WeatherResponse()
            val type = "정보 조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.message == "fail") {
                        _weather.postValue(failResponses)
                    } else {
                        _weather.postValue(response.body)
                    }
                }

                is NetworkResponse.ApiError -> {
                    postValueEvent(0, type)
                }

                is NetworkResponse.NetworkError -> {
                    postValueEvent(1, type)
                }

                is NetworkResponse.UnknownError -> {
                    postValueEvent(2, type)
                }
            }
            hideProgress()
        }
    }

    fun getShuttleBusStopMark() {
        val shuttleBusStopMark = AppPreferences.getShuttleBusStopMark()
        _shuttleBusStopMark.postValue(shuttleBusStopMark)
    }

    fun updateShuttleBusStopMark(shuttleBusStop: ShuttleBusStop, isMakeNewMark: Boolean) {
        if (isMakeNewMark) {
            AppPreferences.updateShuttleBusStopMark(shuttleBusStop)
        } else {
            AppPreferences.updateShuttleBusStopMark(ShuttleBusStop("", 0.0, 0.0, "", false))
        }
        getShuttleBusStopMark()
    }

    fun getKakaoUserInfo() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("사용자 정보 요청 실패", error.toString())
            }
            else if (user != null) {
                _kakaoUser.postValue(user)
            }
        }
    }

    private fun postValueEvent(value: Int, type: String) {
        val msgArrayList = arrayOf(
            "Api 오류 : $type 실패했습니다.",
            "서버 오류 : $type 실패했습니다.",
            "알 수 없는 오류 : $type 실패했습니다."
        )

        when (value) {
            0 -> _msg.postValue(Event(msgArrayList[0]))
            1 -> _msg.postValue(Event(msgArrayList[1]))
            2 -> _msg.postValue(Event(msgArrayList[2]))
        }
    }
}