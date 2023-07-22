package com.ssafy.gumi_life_project.ui.board

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.gumi_life_project.data.model.BoardDetailResponse
import com.ssafy.gumi_life_project.data.model.BoardItem
import com.ssafy.gumi_life_project.data.model.CommentDto
import com.ssafy.gumi_life_project.data.model.Event
import com.ssafy.gumi_life_project.data.repository.board.BoardRepository
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import com.ssafy.gumi_life_project.util.template.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val repository: BoardRepository
) : BaseViewModel() {
    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    private val _comment = MutableLiveData<Event<String>>()
    val comment: LiveData<Event<String>> = _comment

    private val _board = MutableLiveData<List<BoardItem>>()
    val board: LiveData<List<BoardItem>> = _board

    private val _boardDetail = MutableLiveData<BoardDetailResponse>()
    val boardDetail: LiveData<BoardDetailResponse> = _boardDetail

    private val _isBoardClicked = MutableLiveData<Event<Unit>>()
    val isBoardClicked: LiveData<Event<Unit>> = _isBoardClicked

    fun getBoardList() {
        showProgress()
        viewModelScope.launch {
            val response = repository.getBoardList()

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

    fun getBoardDetail(boardNo: String) {
        showProgress()
        viewModelScope.launch {
            val response = repository.getBoardDetail(boardNo)

            val type = "게시판 상세조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    _boardDetail.postValue(response.body)
                    _isBoardClicked.postValue(Event(Unit))
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


    fun writeComment(comment: CommentDto) {
        showProgress()
        viewModelScope.launch {
            val response = repository.writeComment(comment)

            val type = "댓글 생성에"
            when (response) {
                is NetworkResponse.Success -> {
                    _comment.postValue(Event(response.body.message))
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
            boardDetail.value?.boardDetail?.let { getBoardDetail(it.boardNo) }
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