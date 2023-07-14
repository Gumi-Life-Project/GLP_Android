package com.ssafy.gumi_life_project.ui.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.databinding.FragmentBoardDetailBinding
import com.ssafy.gumi_life_project.ui.main.LoadingDialog
import com.ssafy.gumi_life_project.util.template.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardDetailFragment : BaseFragment<FragmentBoardDetailBinding>(
    R.layout.fragment_board_detail
) {
    private val viewModel by activityViewModels<BoardViewModel>()

    override fun onCreateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentBoardDetailBinding {
        return FragmentBoardDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@BoardDetailFragment.viewModel
        }
    }

    override fun init() {
        initToolbar()
        initRecyclerView()
        initObserver()

    }

    private fun initToolbar() {
        bindingNonNull.toolbar.toolbarBackButtonTitle.text =
            resources.getString(R.string.board_title)
        bindingNonNull.toolbar.buttonGoBack.setOnClickListener {
            findNavController().navigate(R.id.action_boardDetailFragment_to_boardListFragment)
        }
    }

    private fun initRecyclerView() {

    }

    private fun initObserver() {
        with(viewModel) {
            errorMsg.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }

            val dialog = LoadingDialog(requireContext())
            isLoading.observe(viewLifecycleOwner) {
                if (isLoading.value!!) {
                    dialog.show()
                } else if (!isLoading.value!!) {
                    dialog.dismiss()
                }
            }
        }
    }
}