package com.ssafy.gumi_life_project.ui.login

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.local.AppPreferences
import com.ssafy.gumi_life_project.databinding.FragmentLoginBinding
import com.ssafy.gumi_life_project.ui.main.MainViewModel
import com.ssafy.gumi_life_project.util.template.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


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
        bindingNonNull.buttonKakaoLogin.setOnClickListener {
            login()
        }

    }

    private fun login() {
        // 카카오계정으로 로그인 공통 callback 구성
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("카카오계정으로 로그인 실패", error.toString())
            } else if (token != null) {
                Log.i("카카오계정으로 로그인 성공 ${token.accessToken}", token.accessToken)
                // jwt 토큰 발급 & sharedPreferences에 jwt 토큰 저장
                viewModel.getJwtToken(token.accessToken)
                activityViewModel.getKakaoUserInfo()
            }
        }
        // 카카오계정으로 로그인
        UserApiClient.instance.loginWithKakaoAccount(
            requireContext(),
            callback = callback
        )

    }

    private fun observeData() {
        viewModel.userResponse.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_loginFragment_to_splashFragment)
        }

    }
}