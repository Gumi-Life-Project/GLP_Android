package com.ssafy.gumi_life_project.ui.board

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.databinding.FragmentBoardWriteBinding
import com.ssafy.gumi_life_project.util.template.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardWriteFragment : BaseFragment<FragmentBoardWriteBinding>(
    R.layout.fragment_board_write
) {
    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBoardWriteBinding {
        return FragmentBoardWriteBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {

    }

}