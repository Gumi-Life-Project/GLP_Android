package com.ssafy.gumi_life_project.ui.login

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.databinding.FragmentLoginBinding
import com.ssafy.gumi_life_project.util.template.BaseFragment

private const val TAG = "LoginFragment_구미"
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        initListener()
        observeData()
    }

    private fun initListener() {
        // 카카오 로그인
        bindingNonNull.buttonKakaoLogin.setOnClickListener{
            login()
        }

        bindingNonNull.imagebuttonLoginMaintain.setOnClickListener {
            viewModel.checkLogin()
        }
    }

    private fun observeData() {
        viewModel.apply {
            isCheckedLogin.observe(viewLifecycleOwner) {
                if(it == false) {
                    bindingNonNull.imagebuttonLoginMaintain.setImageResource(R.drawable.login_unchecked_maintain_button)
                } else {
                    bindingNonNull.imagebuttonLoginMaintain.setImageResource(R.drawable.login_checked_maintain_button)
                }
            }
        }
    }

    private fun login() {
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공1 ${token.accessToken}")
//                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context!!)) {
            UserApiClient.instance.loginWithKakaoTalk(context!!) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context!!, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공2 ${token.accessToken}")
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context!!, callback = callback)
        }

    }
}