package com.ssafy.gumi_life_project.ui.mypage

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.databinding.DialogLogoutBinding

class LogoutDialog : DialogFragment() {
    private lateinit var binding: DialogLogoutBinding
    private val viewModel by viewModels<MypageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogLogoutBinding.inflate(inflater, container, false)
        initListener()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val windowManager = requireActivity().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val deviceWidth = size.x

        dialog?.window?.let { window ->
            val params = window.attributes
            params.width = (deviceWidth * 0.9).toInt()
            window.attributes = params
        }
    }


    private fun initListener() {
        binding.buttonLogoutCancel.setOnClickListener {
            dismiss()
        }

        binding.buttonLogoutConfirm.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.action_mypageFragment_to_loginFragment)
        }
    }

}