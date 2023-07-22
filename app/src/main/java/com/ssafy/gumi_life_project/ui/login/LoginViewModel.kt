package com.ssafy.gumi_life_project.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.gumi_life_project.data.local.AppPreferences
import com.ssafy.gumi_life_project.data.model.Event
import com.ssafy.gumi_life_project.data.model.UserResponse
import com.ssafy.gumi_life_project.data.repository.user.UserRepository
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import com.ssafy.gumi_life_project.util.template.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    private val _userResponse = MutableLiveData<UserResponse>()
    val userResponse: LiveData<UserResponse> = _userResponse


    fun getJwtToken(accessToken: String) {
        showProgress()
        viewModelScope.launch {
            val response = repository.getJwtToken(accessToken)

            val type = "token 정보 조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    _userResponse.postValue(response.body)
                    AppPreferences.initJwtToken(response.body.user.jwt.accessToken)
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