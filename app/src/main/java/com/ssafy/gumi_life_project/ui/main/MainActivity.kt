package com.ssafy.gumi_life_project.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.local.AppPreferences
import com.ssafy.gumi_life_project.databinding.ActivityMainBinding
import com.ssafy.gumi_life_project.util.template.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.navigationHome.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.homeFragment, R.id.boardListFragment -> {
                    binding.navigationHome.visibility = View.VISIBLE
                }
                else -> {
                    binding.navigationHome.visibility = View.GONE
                }
            }
        }

        viewModel.findId()

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

            userId.observe(this@MainActivity) {
                AppPreferences.saveUserId(it)
            }
        }
    }
}