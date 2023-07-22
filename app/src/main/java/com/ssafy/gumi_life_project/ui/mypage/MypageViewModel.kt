package com.ssafy.gumi_life_project.ui.mypage

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.ssafy.gumi_life_project.data.local.AppPreferences
import com.ssafy.gumi_life_project.data.model.BoardItem
import com.ssafy.gumi_life_project.data.model.Event
import com.ssafy.gumi_life_project.data.repository.user.UserRepository
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import com.ssafy.gumi_life_project.util.template.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MypageViewModel @Inject constructor(
    private val repository: UserRepository
): BaseViewModel() {

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    private val _board = MutableLiveData<List<BoardItem>>()
    val board: LiveData<List<BoardItem>> = _board

    fun logout() {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            }
            else {
                Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
    }

    fun getUserBoards() {
        showProgress()
        val jwtToken = AppPreferences.getJwtToken()
        viewModelScope.launch {
            val response = repository.getUserBoards(jwtToken!!)

            val type = "게시판 조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    _board.postValue(response.body.boardList)
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

    fun getUserComments() {
        showProgress()
        val jwtToken = AppPreferences.getJwtToken()
        viewModelScope.launch {
            val response = repository.getUserComments(jwtToken!!)

            val type = "게시판 조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    _board.postValue(response.body.boardList)
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

    fun getUserLikes() {
        showProgress()
        val jwtToken = AppPreferences.getJwtToken()

        viewModelScope.launch {
            val response = repository.getUserLikes(jwtToken!!)

            val type = "게시판 조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    _board.postValue(response.body.boardList)
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