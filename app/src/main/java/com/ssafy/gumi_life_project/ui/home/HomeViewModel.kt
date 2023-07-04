package com.ssafy.gumi_life_project.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.gumi_life_project.data.model.*
import com.ssafy.gumi_life_project.data.repository.home.HomeRepository
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import com.ssafy.gumi_life_project.util.template.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel_구미"
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : BaseViewModel() {

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    private val _showBottomSheetEvent = MutableLiveData<Event<SignalLight>>()
    val showBottomSheetEvent: LiveData<Event<SignalLight>> = _showBottomSheetEvent

    private val _tip = MutableLiveData<Event<List<Tip>>>()
    val tip: LiveData<Event<List<Tip>>> = _tip
    
    private val _weather = MutableLiveData<Event<WeatherResponse>>()
    val weather: LiveData<Event<WeatherResponse>> = _weather


    fun getAllTipList() {
        showProgress()
        viewModelScope.launch {
            val response = repository.getAllTipList()

            val type = "정보 조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    _tip.value = Event(response.body)
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
    
    fun getNowWeather() {
        showProgress()
        viewModelScope.launch {
            val response = repository.getNowWeather()
            Log.d(TAG, "getNowWeather: $response")

            val type = "정보 조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    _weather.value = Event(response.body)
                    Log.d(TAG, "getNowWeather: ${weather.value}")
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

    fun onCrossWorkTimeViewClicked(signalLight: SignalLight) {
        _showBottomSheetEvent.value = Event(signalLight)
    }
}