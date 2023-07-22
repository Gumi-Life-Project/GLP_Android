package com.ssafy.gumi_life_project.ui.settingnickname

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.gumi_life_project.data.model.Event
import com.ssafy.gumi_life_project.data.repository.user.UserRepository
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import com.ssafy.gumi_life_project.util.template.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingNicknameViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {
    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    fun makeNickName(nickName: String) {
        showProgress()
        viewModelScope.launch {
            val response = repository.makeNickName(nickName)

            val type = "닉네임 변경에"
            when (response) {
                is NetworkResponse.Success -> {

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
}