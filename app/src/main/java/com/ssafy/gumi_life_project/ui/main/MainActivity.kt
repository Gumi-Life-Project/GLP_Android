package com.ssafy.gumi_life_project.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.databinding.ActivityMainBinding
import com.ssafy.gumi_life_project.util.template.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeData()
    }

    private fun observeData() {
        with(viewModel) {
            errorMsg.observe(this@MainActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }

            val dialog = LoadingDialog(this@MainActivity)
            isLoading.observe(this@MainActivity) {
                if (isLoading.value!!) {
                    dialog.show()
                } else if (!isLoading.value!!) {
                    dialog.dismiss()
                }
            }
        }
    }
}