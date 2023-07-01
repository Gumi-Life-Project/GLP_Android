package com.ssafy.gumi_life_project.ui.splash


import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.databinding.ActivitySplashBinding
import com.ssafy.gumi_life_project.ui.main.MainActivity
import com.ssafy.gumi_life_project.util.template.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        animateLoading()
        moveToMainActivity()

    }

    private fun animateLoading() {
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            var currentImageViewIndex = 0
            val imageViews = listOf(binding.imageviewSplash1, binding.imageviewSplash2, binding.imageviewSplash3, binding.imageviewSplash4, binding.imageviewSplash5)

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

    private fun moveToMainActivity() {
        // 1초 후에 다른 Activity로 이동
        Handler(Looper.getMainLooper()).postDelayed({
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}