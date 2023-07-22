package com.ssafy.gumi_life_project.ui.login

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.Constraints
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.databinding.FragmentLoginBinding
import com.ssafy.gumi_life_project.ui.main.MainViewModel
import com.ssafy.gumi_life_project.util.template.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "LoginFragment_구미"
@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val activityViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@LoginFragment.viewModel
            mainViewModel = this@LoginFragment.activityViewModel
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

    }

    private fun login() {
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인 할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                // jwt 토큰 발급 & sharedPrefences에 jwt 토큰 저장
                viewModel.getJwtToken(token.accessToken)
                findNavController().navigate(R.id.action_loginFragment_to_splashFragment)
                // 최초 로그인 -> settingNicknameFragment
                // 최초 로그인이 아닐 시 -> homeFragment
//                findNavController().navigate(R.id.action_loginFragment_to_settingNicknameFragment)
//                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                if (error != null) {
                    Log.e(Constraints.TAG, "로그인 실패", error)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        Log.e(Constraints.TAG, "로그인 취소", error)
                    }
                } else if (token != null) {
                    Log.d(TAG, "login: 카카오앱으로 로그인 성공 $token")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(
                requireContext(),
                callback = callback
            )
        }

//        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
//        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
//            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
//                if (error != null) {
//                    Log.e(TAG, "카카오톡으로 로그인 실패", error)
//
//                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
//                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
//                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
//                        return@loginWithKakaoTalk
//                    }
//
//                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
//                    UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
//                } else if (token != null) {
//                    Log.i(TAG, "카카오톡으로 로그인 성공2 ${token.accessToken}")
//                }
//            }
//        } else {
//            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
//        }

    }
    
    private fun observeData() {
        viewModel.userResponse.observe(viewLifecycleOwner) {
            Log.d(TAG, "observeData: $it")
            activityViewModel.getMemberInfo(it.user.jwt.accessToken)
        }

        // usernickname이 null이면 닉네임 설정 화면으로 이동
//        activityViewModel.memberInfo.observe(viewLifecycleOwner) {
//            Log.d(TAG, "observeData: $it")
//            if (it.usernickname == null) {
//                findNavController().navigate(R.id.action_loginFragment_to_settingNicknameFragment)
//            } else {
//                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//            }
//
//        }
    }
}