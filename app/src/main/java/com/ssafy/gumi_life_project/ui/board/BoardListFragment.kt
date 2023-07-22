package com.ssafy.gumi_life_project.ui.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.model.BoardItem
import com.ssafy.gumi_life_project.databinding.FragmentBoardListBinding
import com.ssafy.gumi_life_project.ui.main.LoadingDialog
import com.ssafy.gumi_life_project.util.template.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardListFragment : BaseFragment<FragmentBoardListBinding>(
    R.layout.fragment_board_list
) {
    private val viewModel by activityViewModels<BoardViewModel>()
    private lateinit var adapter: BoardListAdapter

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBoardListBinding {
        return FragmentBoardListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@BoardListFragment.viewModel
        }
    }

    override fun init() {
        initToolbar()
        initRecyclerView()
        initObserver()
        initListener()

        bindingNonNull.swipelayoutBoard.setOnRefreshListener {
            viewModel.getBoardList()
            bindingNonNull.swipelayoutBoard.isRefreshing = false
        }
    }

    private fun initToolbar() {
        bindingNonNull.toolbar.toolbarBackButtonTitle.text =
            resources.getString(R.string.board_title)
        bindingNonNull.toolbar.buttonGoBack.setOnClickListener {
            findNavController().navigate(R.id.action_boardListFragment_to_homeFragment)
        }
    }

    private fun initRecyclerView() {
        viewModel.getBoardList()

        adapter = BoardListAdapter()
        adapter.onItemClickListener = object : BoardListAdapter.OnItemClickListener {
            override fun onItemClick(boardItem: BoardItem) {
                viewModel.getBoardDetail(boardItem.boardNo)
            }
        }
        bindingNonNull.recyclerviewBoardList.adapter = adapter
    }

    private fun initObserver() {
        with(viewModel) {
            board.observe(viewLifecycleOwner) {
                adapter.setBoardList(it)
            }

            isBoardClicked.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let {
                    findNavController().navigate(R.id.action_boardListFragment_to_boardDetailFragment)
                }
            }

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

    private fun initListener() {
        bindingNonNull.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_boardListFragment_to_boardWriteFragment)
        }
    }
}