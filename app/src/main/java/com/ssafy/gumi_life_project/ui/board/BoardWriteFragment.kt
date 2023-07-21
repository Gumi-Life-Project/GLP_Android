package com.ssafy.gumi_life_project.ui.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.model.BoardWriteItem
import com.ssafy.gumi_life_project.data.model.BoardWriteResponseType
import com.ssafy.gumi_life_project.databinding.FragmentBoardWriteBinding
import com.ssafy.gumi_life_project.util.template.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardWriteFragment : BaseFragment<FragmentBoardWriteBinding>(
    R.layout.fragment_board_write
) {
    private val viewModel by activityViewModels<BoardViewModel>()
    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBoardWriteBinding {
        return FragmentBoardWriteBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@BoardWriteFragment.viewModel
        }
    }

    override fun init() {
        initToolbar()
        initListener()
        initObserver()
    }

    private fun initToolbar() {
        bindingNonNull.toolbarBoardWrite.toolbarBackButtonTitle.text =
            resources.getString(R.string.board_write_toolbar_title)
        bindingNonNull.toolbarBoardWrite.buttonGoBack.setOnClickListener {
            findNavController().navigate(R.id.action_boardWriteFragment_to_boardListFragment)
        }
    }

    private fun initListener() {
        bindingNonNull.buttonWrite.setOnClickListener {
            val title = bindingNonNull.edittextTitle.text.toString()
            val content = bindingNonNull.edittextContent.text.toString()
            if (title != "" && content != "") {
                viewModel.writeBoard(BoardWriteItem(title, content))
            } else {
                showToast(getString(R.string.board_write_toast_message))
            }
        }
    }

    private fun initObserver() {
        viewModel.writeResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                if (it == BoardWriteResponseType.SUCCESS) {
                    showToast(getString(R.string.board_write_response_success))
                    findNavController().navigate(R.id.action_boardWriteFragment_to_boardListFragment)
                } else if (it == BoardWriteResponseType.FAIL) {
                    showToast(getString(R.string.board_write_response_fail))
                    findNavController().navigate(R.id.action_boardWriteFragment_to_boardListFragment)
                }
            }
        }
    }
}