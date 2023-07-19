package com.ssafy.gumi_life_project.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.util.template.BaseViewModel

private const val TAG = "LoginViewModel_구미"

class LoginViewModel : BaseViewModel() {
    private var _isCheckedLogin = MutableLiveData(false)
    val isCheckedLogin: LiveData<Boolean> = _isCheckedLogin

    fun checkLogin() {
        if (isCheckedLogin.value == false) {
            _isCheckedLogin.postValue(true)
        } else {
            _isCheckedLogin.postValue(false)
        }
    }
}