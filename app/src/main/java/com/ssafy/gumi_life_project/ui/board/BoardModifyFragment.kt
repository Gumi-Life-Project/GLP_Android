package com.ssafy.gumi_life_project.ui.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.model.BoardModifyResponseType
import com.ssafy.gumi_life_project.databinding.FragmentBoardModifyBinding
import com.ssafy.gumi_life_project.util.template.BaseFragment

class BoardModifyFragment : BaseFragment<FragmentBoardModifyBinding>(
    R.layout.fragment_board_modify
) {
    private val viewModel by activityViewModels<BoardViewModel>()
    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBoardModifyBinding {
        return FragmentBoardModifyBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@BoardModifyFragment.viewModel
        }
    }


    override fun init() {
        initToolbar()
        initListener()
        initObserver()
    }

    private fun initToolbar() {
        bindingNonNull.toolbarBoardModify.toolbarBackButtonTitle.text =
            resources.getString(R.string.board_modify_toolbar_title)
        bindingNonNull.toolbarBoardModify.buttonGoBack.setOnClickListener {
            findNavController().navigate(R.id.action_boardModifyFragment_to_boardDetailFragment)
        }
    }

    private fun initListener() {
        bindingNonNull.buttonModify.setOnClickListener {
            val title = bindingNonNull.edittextModifyTitle.text.toString()
            val content = bindingNonNull.edittextModifyContent.text.toString()
            if (title != "" && content != "") {
                viewModel.modifyBoard(title, content)
            } else {
                showToast(getString(R.string.board_write_toast_message))
            }
        }
    }

    private fun initObserver() {
        viewModel.modifyResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                if (it == BoardModifyResponseType.SUCCESS) {
                    showToast(getString(R.string.board_modify_response_success))
                } else if (it == BoardModifyResponseType.FAIL) {
                    showToast(getString(R.string.board_modify_response_fail))
                }
                findNavController().navigate(R.id.action_boardModifyFragment_to_boardDetailFragment)
            }
        }
    }

}