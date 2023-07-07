package com.ssafy.gumi_life_project.ui.shuttlebus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.gumi_life_project.data.local.AppPreferences
import com.ssafy.gumi_life_project.data.model.ShuttleBusLine
import com.ssafy.gumi_life_project.data.model.ShuttleBusStop
import com.ssafy.gumi_life_project.util.template.BaseViewModel

class ShuttleBusViewModel : BaseViewModel() {
    private var shuttleBusLineList: MutableList<ShuttleBusLine> = mutableListOf()
    private val _shuttleBusLineListLiveData: MutableLiveData<MutableList<ShuttleBusLine>> =
        MutableLiveData()
    val shuttleBusLineListLiveData: LiveData<MutableList<ShuttleBusLine>>
        get() = _shuttleBusLineListLiveData

    fun getShuttleBusLineList() {
        shuttleBusLineList = AppPreferences.getShuttleBusInfo()
        _shuttleBusLineListLiveData.postValue(shuttleBusLineList)
    }

    fun updateShuttleBusStopList(shuttleBusStop: ShuttleBusStop) {
        shuttleBusLineList.forEach { shuttleBusLine ->
            shuttleBusLine.stopList.forEach { oldShuttleBusStop ->
                if (shuttleBusStop === oldShuttleBusStop) {
                    oldShuttleBusStop.isMarked = !oldShuttleBusStop.isMarked
                } else if (oldShuttleBusStop.isMarked == true) {
                    oldShuttleBusStop.isMarked = false
                }
            }
        }
        _shuttleBusLineListLiveData.postValue(shuttleBusLineList)
        AppPreferences.updateShuttleBusInfo(shuttleBusLineList)
    }
}