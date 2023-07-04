package com.ssafy.gumi_life_project.ui.shuttlebus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.gumi_life_project.data.local.AppPreferences
import com.ssafy.gumi_life_project.data.model.ShuttleBusLine
import com.ssafy.gumi_life_project.util.template.BaseViewModel

class ShuttleBusViewModel : BaseViewModel() {
    private val _shuttleBusLineList: MutableLiveData<MutableList<ShuttleBusLine>> =
        MutableLiveData()
    val shuttleBusLineList: LiveData<MutableList<ShuttleBusLine>>
        get() = _shuttleBusLineList

    fun getShuttleBusLineList() {
        val lineList = AppPreferences.getShuttleBusInfo()
        _shuttleBusLineList.postValue(lineList)
    }


}