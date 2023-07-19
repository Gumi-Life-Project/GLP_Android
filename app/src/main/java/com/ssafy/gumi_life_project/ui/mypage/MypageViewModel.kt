package com.ssafy.gumi_life_project.ui.mypage

import android.util.Log
import com.kakao.sdk.user.UserApiClient
import com.ssafy.gumi_life_project.util.template.BaseViewModel

private const val TAG = "MypageViewModel_구미"
class MypageViewModel : BaseViewModel() {
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
}