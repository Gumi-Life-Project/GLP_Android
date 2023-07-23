package com.ssafy.gumi_life_project.ui.board

import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.model.Comment
import com.ssafy.gumi_life_project.data.model.CommentDto
import com.ssafy.gumi_life_project.data.model.ReplyDto
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
    private val commentAdapter = CommentAdapter()
    private var selectedCommentId: String? = null
    var likeStatus: Boolean = false
    var likeCount: Int = 0
    var boardId: String = ""

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

        commentAdapter.onCommentClick = {
            if (selectedCommentId == it.commentNo) {
                deselectComment()
            } else {
                selectComment(it)
            }
        }

        bindingNonNull.imageviewHeart.setOnClickListener {
            if(boardId.isBlank()) return@setOnClickListener
            if(likeStatus) {
                viewModel.deleteLike(boardId)
            } else {
                viewModel.updateLike(boardId)
            }
        }
    }


    private fun selectComment(comment: Comment) {
        selectedCommentId = comment.commentNo
        bindingNonNull.textviewReadReply.text =
            getString(R.string.board_comment_user_reply, comment.writerName)
        bindingNonNull.textviewReadReply.visibility = View.VISIBLE
    }

    private fun deselectComment() {
        selectedCommentId = null
        bindingNonNull.textviewReadReply.visibility = View.GONE
        commentAdapter.changeCommentColor()
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
        if (comment == "") {
            showToast("내용을 입력해주세요.")
            return
        }
        if (selectedCommentId != null) {
            viewModel.writeReply(ReplyDto(boardNo, selectedCommentId!!, comment))
            deselectComment()
        } else {
            viewModel.writeComment(CommentDto(boardNo, comment))
        }
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
                    if (comment == "success") {
                        viewModel.boardDetail.value?.boardDetail?.let { viewModel.getBoardDetail(it.boardNo) }
                        showToast("댓글 작성 완료")
                        bindingNonNull.edittextComment.text.clear()
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
                likeStatus = board.boardDetail.likeStatus == 1
                likeCount = board.boardDetail.likesNum
                boardId = board.boardDetail.boardNo

                bindingNonNull.recyclerviewComment.apply {
                    adapter = commentAdapter
                    (adapter as? CommentAdapter)?.submitList(board.comments)
                    val dividerItemDecoration =
                        DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
                    addItemDecoration(dividerItemDecoration)
                }
            }

            boardDislike.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let {
                    if (it == "success") {
                        likeCount--
                        likeStatus = false
                        bindingNonNull.imageviewHeart.setImageResource(R.drawable.icon__heart_)
                        bindingNonNull.textviewHeart.text = likeCount.toString()
                    }
                }
            }

            boardLike.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let {
                    if (it == "success") {
                        likeCount++
                        likeStatus = true
                        bindingNonNull.imageviewHeart.setImageResource(R.drawable.icon_heart)
                        bindingNonNull.textviewHeart.text = likeCount.toString()
                    }
                }
            }
        }
    }
}