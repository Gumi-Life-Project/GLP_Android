package com.ssafy.gumi_life_project.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.databinding.FragmentMypageBinding
import com.ssafy.gumi_life_project.util.template.BaseFragment

class MypageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {
    private val viewModel by viewModels<MypageViewModel>()
    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMypageBinding {
        return FragmentMypageBinding.inflate(layoutInflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        bindingNonNull.viewModel = viewModel
        initToolBar()
        initListener()
    }

    private fun initToolBar() {
        bindingNonNull.toolBarMypage.toolbarBackButtonTitle.text =
            resources.getString(R.string.user_info_tool_bar_title)
        bindingNonNull.toolBarMypage.toolbarBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_mypageFragment_to_homeFragment)
        }
    }

    private fun initListener() {
        bindingNonNull.textviewAccountLogout.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.action_mypageFragment_to_loginFragment)
        }
    }

}