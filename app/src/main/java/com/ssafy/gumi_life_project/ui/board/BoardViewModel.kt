package com.ssafy.gumi_life_project.ui.board

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ssafy.gumi_life_project.data.model.*
import com.ssafy.gumi_life_project.data.repository.board.BoardRepository
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import com.ssafy.gumi_life_project.util.template.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val repository: BoardRepository
) : BaseViewModel() {
    private val gson = Gson()

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    private val _comment = MutableLiveData<Event<String>>()
    val comment: LiveData<Event<String>> = _comment

    private val _board = MutableLiveData<List<BoardItem>>()
    val board: LiveData<List<BoardItem>> = _board

    private val _boardDetail = MutableLiveData<BoardDetailResponse>()
    val boardDetail: LiveData<BoardDetailResponse> = _boardDetail

    private val _writeResponse = MutableLiveData<Event<BoardWriteResponseType>>()
    val writeResponse: LiveData<Event<BoardWriteResponseType>> = _writeResponse

    private val _boardNo = MutableLiveData<String>()
    val boardNo: LiveData<String> = _boardNo

    fun getBoardList() {
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
        }
    }

    fun saveBoardNo(boardNo: String) {
        _boardNo.value = boardNo
    }


    fun getBoardDetail(boardNo: String) {
        showProgress()
        viewModelScope.launch {
            val response = repository.getBoardDetail(boardNo)

            val type = "게시판 상세조회에"
            when (response) {
                is NetworkResponse.Success -> {
                    _boardDetail.postValue(response.body)
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

    fun writeBoard(boardWriteItem: BoardWriteItem) {
        showProgress()
        viewModelScope.launch {
            val requestBody =
                gson.toJson(boardWriteItem).toRequestBody("application/json".toMediaTypeOrNull())

            var response: NetworkResponse<BoardWriteResponse, ErrorResponse>? = null
            response = repository.writeBoard(requestBody, null)

            val type = "게시글 작성에"
            when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.message == "fail") {
                        postValueEvent(2, type)
                        _writeResponse.postValue(Event(BoardWriteResponseType.FAIL))
                    } else {
                        _writeResponse.postValue(Event(BoardWriteResponseType.SUCCESS))
                    }
                }

                is NetworkResponse.ApiError -> {
                    postValueEvent(0, type)
                    _writeResponse.postValue(Event(BoardWriteResponseType.FAIL))
                }

                is NetworkResponse.NetworkError -> {
                    postValueEvent(1, type)
                    _writeResponse.postValue(Event(BoardWriteResponseType.FAIL))
                }

                is NetworkResponse.UnknownError -> {
                    postValueEvent(2, type)
                    _writeResponse.postValue(Event(BoardWriteResponseType.FAIL))
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