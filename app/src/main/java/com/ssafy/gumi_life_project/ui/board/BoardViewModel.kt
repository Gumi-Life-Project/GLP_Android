package com.ssafy.gumi_life_project.ui.board

import android.util.Log
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

private const val TAG = "BoardViewModel"
@HiltViewModel
class BoardViewModel @Inject constructor(
    private val repository: BoardRepository
) : BaseViewModel() {
    private val gson = Gson()

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    private val _boardLike = MutableLiveData<Event<String>>()
    val boardLike: LiveData<Event<String>> = _boardLike

    private val _boardDislike = MutableLiveData<Event<String>>()
    val boardDislike: LiveData<Event<String>> = _boardDislike

    private val _comment = MutableLiveData<Event<String>>()
    val comment: LiveData<Event<String>> = _comment

    private val _board = MutableLiveData<List<BoardItem>>()
    val board: LiveData<List<BoardItem>> = _board

    private val _boardDetail = MutableLiveData<BoardDetailResponse>()
    val boardDetail: LiveData<BoardDetailResponse> = _boardDetail

    private val _writeResponse = MutableLiveData<Event<BoardWriteResponseType>>()
    val writeResponse: LiveData<Event<BoardWriteResponseType>> = _writeResponse

    private val _modifyResponse = MutableLiveData<Event<BoardModifyResponseType>>()
    val modifyResponse: LiveData<Event<BoardModifyResponseType>> = _modifyResponse

    private val _boardNo = MutableLiveData<String>()
    val boardNo: LiveData<String> = _boardNo

    private val _commentCount = MutableLiveData<String>()
    val commentCount: LiveData<String> = _commentCount

    var report: Report? = null

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
                    _commentCount.postValue(response.body.comments.size.toString())
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

    fun updateLike(boardNo: String) {
        showProgress()
        viewModelScope.launch {
            val response = repository.updateLike(boardNo)

            val type = "좋아요 선택에"
            when (response) {
                is NetworkResponse.Success -> {
                    _boardLike.postValue(Event(response.body.message))
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

    fun deleteLike(boardNo: String) {
        showProgress()
        viewModelScope.launch {
            val response = repository.deleteLike(boardNo)

            val type = "좋아요 취소에"
            when (response) {
                is NetworkResponse.Success -> {
                    _boardDislike.postValue(Event(response.body.message))
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

    fun deleteBoard(boardNo: String, boardWriterId: String) {
        showProgress()
        viewModelScope.launch {
            val response = repository.deleteBoard(boardNo, boardWriterId)

            val type = "게시글 삭제에"
            when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.message == "success") {
                        _msg.postValue(Event("게시글이 삭제되었습니다."))
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

    fun deleteComment(commentNo: String, commentWriterId: String) {
        showProgress()
        viewModelScope.launch {
            val response = repository.deleteComment(commentNo, commentWriterId)

            val type = "댓글 삭제에"
            when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.message == "success") {
                        _msg.postValue(Event("댓글이 삭제되었습니다."))
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

    fun deleteReply(replyNo: String, replyWriterId: String) {
        showProgress()
        viewModelScope.launch {
            val response = repository.deleteReply(replyNo, replyWriterId)

            val type = "대댓글 삭제에"
            when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.message == "success") {
                        _msg.postValue(Event("대댓글이 삭제되었습니다."))
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

    fun writeReply(replyDto: ReplyDto) {
        showProgress()
        viewModelScope.launch {
            val response = repository.writeReply(replyDto)

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

    fun modifyBoard(title: String, content: String) {
        showProgress()
        viewModelScope.launch {
            if (_boardDetail.value != null) {
                val modifiedBoard = BoardItem(
                    _boardDetail.value!!.boardDetail.boardNo,
                    title,
                    content,
                    _boardDetail.value!!.boardDetail.hit,
                    _boardDetail.value!!.boardDetail.createDate,
                    _boardDetail.value!!.boardDetail.updateDate,
                    _boardDetail.value!!.boardDetail.folder,
                    _boardDetail.value!!.boardDetail.originName,
                    _boardDetail.value!!.boardDetail.saveName,
                    _boardDetail.value!!.boardDetail.writerId,
                    _boardDetail.value!!.boardDetail.writerName,
                    _boardDetail.value!!.boardDetail.likesNum,
                    _boardDetail.value!!.boardDetail.likeStatus
                )

                val requestBody =
                    gson.toJson(modifiedBoard).toRequestBody("application/json".toMediaTypeOrNull())

                var response: NetworkResponse<BoardModifyResponse, ErrorResponse>? = null
                response = repository.modifyBoard(requestBody, null)

                val type = "게시글 수정에"
                when (response) {
                    is NetworkResponse.Success -> {
                        if (response.body.message == "fail") {
                            postValueEvent(2, type)
                            _modifyResponse.postValue(Event(BoardModifyResponseType.FAIL))
                        } else {
                            _modifyResponse.postValue(Event(BoardModifyResponseType.SUCCESS))
                        }
                    }

                    is NetworkResponse.ApiError -> {
                        postValueEvent(0, type)
                        _modifyResponse.postValue(Event(BoardModifyResponseType.FAIL))
                    }

                    is NetworkResponse.NetworkError -> {
                        postValueEvent(1, type)
                        _modifyResponse.postValue(Event(BoardModifyResponseType.FAIL))
                    }

                    is NetworkResponse.UnknownError -> {
                        postValueEvent(2, type)
                        _modifyResponse.postValue(Event(BoardModifyResponseType.FAIL))
                    }
                }
                hideProgress()
            }
        }
    }

    fun setReport(comment: Comment?) {
        if (comment != null) { //댓글 신고
            report = Report(
                _boardDetail.value!!.boardDetail.boardNo,
                "",
                comment.writerId
            )
        } else { //게시글 신고
            report = Report(
                _boardDetail.value!!.boardDetail.boardNo,
                "",
                _boardDetail.value!!.boardDetail.writerId.toString()
            )
        }
    }

    fun setReportType(type: String) {
        report?.report = type
    }

    fun report() {
        showProgress()
        if(report != null){
            viewModelScope.launch {
                val response = repository.report(report!!)

                val type = "신고에"
                when (response) {
                    is NetworkResponse.Success -> {
                        Log.d(TAG, "report: ${response}")
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