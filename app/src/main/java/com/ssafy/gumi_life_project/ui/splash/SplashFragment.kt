package com.ssafy.gumi_life_project.ui.splash

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.auth.AuthApiClient
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.local.AppPreferences
import com.ssafy.gumi_life_project.databinding.FragmentSplashBinding
import com.ssafy.gumi_life_project.ui.main.MainViewModel
import com.ssafy.gumi_life_project.util.template.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(
    R.layout.fragment_splash
) {
    private val activityViewModel by activityViewModels<MainViewModel>()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        with(activityViewModel) {
            getAllTipList()
            getNowWeather()
            getShuttleBusStopMark()
            getMealList()
        }
        observeData()
        animateLoading()
        moveToHomeFragment()
    }

    private fun animateLoading() {
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            var currentImageViewIndex = 0
            val imageViews = listOf(
                bindingNonNull.imageviewSplash1,
                bindingNonNull.imageviewSplash2,
                bindingNonNull.imageviewSplash3,
                bindingNonNull.imageviewSplash4,
                bindingNonNull.imageviewSplash5
            )

            override fun run() {
                // 현재 이미지 뷰를 아이콘2 상태로 변경
                imageViews[currentImageViewIndex].setImageResource(R.drawable.icon_splash_dark)

                // 첫 번째 이미지 뷰가 아니라면, 이전 이미지 뷰를 다시 아이콘1 상태로 변경
                if (currentImageViewIndex > 0) {
                    imageViews[currentImageViewIndex - 1].setImageResource(R.drawable.icon_splash_light)
                }

                currentImageViewIndex++

                if (currentImageViewIndex == imageViews.size) {
                    // 마지막 이미지뷰를 아이콘 2 상태로 바꿨다가 아이콘 1 상태로 돌아갈 때까지 딜레이를 추가
                    handler.postDelayed({
                        // 마지막 이미지 뷰를 다시 아이콘 1 상태로 변경
                        imageViews[currentImageViewIndex - 1].setImageResource(R.drawable.icon_splash_light)
                        currentImageViewIndex = 0
                        this.run() // 애니메이션을 다시 시작
                    }, 200)
                } else {
                    handler.postDelayed(this, 200)
                }
            }
        }

        handler.post(runnable)
    }

    private fun moveToHomeFragment() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (AuthApiClient.instance.hasToken()) {
                val jwtToken = AppPreferences.getJwtToken()
                if (jwtToken != null) {
                    activityViewModel.apply {
                        getMemberInfo()
                    }
                }
            } else { // 토큰이 없으면 loginFragment로 이동
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }

        }, 1500)
    }

    private fun observeData() {
        activityViewModel.apply {
            memberInfo.observe(viewLifecycleOwner) {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
//                if (it.usernickname == null) {
//                    findNavController().navigate(R.id.action_splashFragment_to_settingNicknameFragment)
//                } else {
//                    // 닉네임이 null이 아니라면 homeFragment로 넘어감
//                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
//                }

            }
        }
    }

}