package com.ssafy.gumi_life_project.ui.settingnickname

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.databinding.FragmentSettingNicknameBinding
import com.ssafy.gumi_life_project.util.template.BaseFragment

class SettingNicknameFragment : BaseFragment<FragmentSettingNicknameBinding>(
    R.layout.fragment_setting_nickname
) {
    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingNicknameBinding {
        return FragmentSettingNicknameBinding.inflate(inflater, container, false)
    }

    override fun init() {
    }
}