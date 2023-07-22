package com.ssafy.gumi_life_project.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.databinding.FragmentMypageBinding
import com.ssafy.gumi_life_project.ui.main.MainViewModel
import com.ssafy.gumi_life_project.util.template.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MypageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {
    private val viewModel by viewModels<MypageViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()

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
        // 내가 쓴 글
        bindingNonNull.layoutBoardWrite.setOnClickListener {
            viewModel.getUserBoards()
        }
        // 댓글 단 글
        bindingNonNull.layoutBoardComment.setOnClickListener {
            viewModel.getUserComments()
        }
        // 좋아요한 글
        bindingNonNull.layoutBoardLike.setOnClickListener {
            viewModel.getUserLikes()
        }

        // 로그아웃
        bindingNonNull.textviewAccountLogout.setOnClickListener {
            val dialog = LogoutDialog()
            dialog.show(childFragmentManager, "logoutDialogTag")
        }

        // 닉네임 변경
        bindingNonNull.textviewAccountNicknameModify.setOnClickListener {
            findNavController().navigate(R.id.action_mypageFragment_to_settingNicknameFragment)
        }


    }

}