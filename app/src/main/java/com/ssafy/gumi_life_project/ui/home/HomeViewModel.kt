package com.ssafy.gumi_life_project.ui.home

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.gumi_life_project.data.model.Event
import com.ssafy.gumi_life_project.data.model.LightTime
import com.ssafy.gumi_life_project.data.model.SignalLight
import com.ssafy.gumi_life_project.data.repository.home.HomeRepository
import com.ssafy.gumi_life_project.util.CrossWorkTimeList
import com.ssafy.gumi_life_project.util.template.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : BaseViewModel() {

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    private val _showBottomSheetEvent = MutableLiveData<Event<SignalLight>>()
    val showBottomSheetEvent: LiveData<Event<SignalLight>> = _showBottomSheetEvent

    private var _isRunning = MutableLiveData(false)
    val isRunning: LiveData<Boolean> = _isRunning

    private var _isInServiceTime = MutableLiveData(false)
    val isInServiceTime: LiveData<Boolean> = _isInServiceTime

    private val _timeText1 = MutableLiveData<LightTime>()
    val timeText1: LiveData<LightTime> = _timeText1

    private val _timeText2 = MutableLiveData<LightTime>()
    val timeText2: LiveData<LightTime> = _timeText2

    private val _timeText3 = MutableLiveData<LightTime>()
    val timeText3: LiveData<LightTime> = _timeText3

    private fun postValueEvent(value: Int, type: String) {
        val msgArrayList = arrayOf(
            "Api 오류 : $type 실패했습니다.", "서버 오류 : $type 실패했습니다.", "알 수 없는 오류 : $type 실패했습니다."
        )

        when (value) {
            0 -> _msg.postValue(Event(msgArrayList[0]))
            1 -> _msg.postValue(Event(msgArrayList[1]))
            2 -> _msg.postValue(Event(msgArrayList[2]))
        }
    }

    fun onCrossWorkTimeViewClicked(signalLight: SignalLight) {
        _showBottomSheetEvent.postValue(Event(signalLight))
    }

    fun loadAndSetTriggerTimes() {
        showProgress()

        _isInServiceTime.postValue(isInServiceTime())

        viewModelScope.launch {
            val triggerTimes = CrossWorkTimeList.getTriggerTimes()

            _timeText1.value = SignalLight.SIGNAL_LIGHT_1.calculateRemainingTime(triggerTimes[0])
            _timeText2.value = SignalLight.SIGNAL_LIGHT_2.calculateRemainingTime(triggerTimes[1])
            _timeText3.value = SignalLight.SIGNAL_LIGHT_3.calculateRemainingTime(triggerTimes[2])

            hideProgress()
        }
    }

    private fun isInServiceTime(): Boolean {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        return currentHour == 8
    }

    private val handler = Handler(Looper.getMainLooper())
    private val runnableCode: Runnable = object : Runnable {
        override fun run() {
            loadAndSetTriggerTimes()
            handler.postDelayed(this, 1000)
        }
    }

    fun startOrStopPeriodicTask() {
        if (_isRunning.value == true) {
            stopPeriodicTask()
        } else {
            startPeriodicTask()
        }
    }

    private fun startPeriodicTask() {
        handler.post(runnableCode)
        _isRunning.postValue(true)
    }

    private fun stopPeriodicTask() {
        handler.removeCallbacks(runnableCode)
        _isRunning.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacks(runnableCode)
    }
}