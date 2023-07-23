package com.ssafy.gumi_life_project.ui.board

import android.os.IBinder
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.model.CommentDto
import com.ssafy.gumi_life_project.databinding.FragmentBoardDetailBinding
import com.ssafy.gumi_life_project.ui.board.comment.CommentAdapter
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
            boardDetail = this@BoardDetailFragment
        }
    }

    override fun init() {
        initToolbar()
        initObserver()
        initData()

        bindingNonNull.layoutSwipe.setOnRefreshListener {
            viewModel.boardDetail.value?.boardDetail?.let { viewModel.getBoardDetail(it.boardNo) }
            bindingNonNull.layoutSwipe.isRefreshing = false
        }
    }

    private fun initData() {
        viewModel.boardNo.value?.let { viewModel.getBoardDetail(it) }
    }


    private fun initToolbar() {
        bindingNonNull.toolbar.toolbarBackButtonTitle.text =
            resources.getString(R.string.board_title)
        bindingNonNull.toolbar.buttonGoBack.setOnClickListener {
            findNavController().navigate(R.id.action_boardDetailFragment_to_boardListFragment)
        }

        bindingNonNull.imageviewMenu.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), bindingNonNull.imageviewMenu)
            popupMenu.inflate(R.menu.menu_board)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.button_board_notice -> {
                        // Handle menu item 1 click
                        true
                    }
                    R.id.button_board_delete -> {
                        // Handle menu item 2 click
                        true
                    }
                    R.id.button_board_update -> {
                        // Handle menu item 2 click
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show() // Show the menu
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_board, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.button_board_delete -> {

                return true
            }
            R.id.button_board_update -> {

                return true
            }
            R.id.button_board_notice -> {

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    fun writeComment(boardNo: String) {
        val comment = bindingNonNull.edittextComment.text.toString()
        if(comment == "") {
            showToast("내용을 입력해주세요.")
            return
        }
        viewModel.writeComment(CommentDto(boardNo, comment))
    }

    private fun initObserver() {
        with(viewModel) {
            errorMsg.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }

            comment.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let { comment ->
                    if(comment == "success") {
                        viewModel.boardDetail.value?.boardDetail?.let { viewModel.getBoardDetail(it.boardNo) }
                        showToast("댓글 작성 완료")
                        bindingNonNull.edittextComment.setText("")
                    }
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

            boardDetail.observe(viewLifecycleOwner) { board ->
                bindingNonNull.recyclerviewComment.apply {
                    adapter = CommentAdapter()
                    (adapter as? CommentAdapter)?.submitList(board.comments)
                    val dividerItemDecoration =
                        DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
                    addItemDecoration(dividerItemDecoration)
                }
            }
        }
    }
}