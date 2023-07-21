package com.ssafy.gumi_life_project.ui.settingnickname

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.databinding.FragmentSettingNicknameBinding
import com.ssafy.gumi_life_project.ui.login.LoginViewModel
import com.ssafy.gumi_life_project.util.template.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "SettingNicknameFragment_구미"
@AndroidEntryPoint
class SettingNicknameFragment : BaseFragment<FragmentSettingNicknameBinding>(
    R.layout.fragment_setting_nickname
) {
    private val viewModel by viewModels<SettingNicknameViewModel>()
    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingNicknameBinding {
        return FragmentSettingNicknameBinding.inflate(inflater, container, false).apply {
            viewModel = this@SettingNicknameFragment.viewModel
        }
    }

    override fun init() {
        initToolBar()
        initListener()
    }

    private fun initToolBar() {
        bindingNonNull.toolbarSettingNickname.toolBarTitle.text = resources.getString(R.string.setting_nickname_title)
    }

    private fun initListener() {
        bindingNonNull.buttonNicknameSetting.setOnClickListener {
            val nickName = bindingNonNull.edittextInputNickname.text.toString()
            viewModel.makeNickName(nickName)
            Log.d(TAG, "initListener: $nickName")
            findNavController().navigate(R.id.action_settingNicknameFragment_to_homeFragment)
        }
    }
}