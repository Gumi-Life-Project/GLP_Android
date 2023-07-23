package com.ssafy.gumi_life_project.ui.board

import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.model.*
import com.ssafy.gumi_life_project.databinding.FragmentBoardDetailBinding
import com.ssafy.gumi_life_project.ui.board.comment.CommentAdapter
import com.ssafy.gumi_life_project.ui.main.LoadingDialog
import com.ssafy.gumi_life_project.util.showDialog
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
    lateinit var boardItem: BoardItem

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
            if (boardItem.boardNo.isBlank()) return@setOnClickListener
            if (likeStatus) {
                viewModel.deleteLike(boardItem.boardNo)
            } else {
                viewModel.updateLike(boardItem.boardNo)
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
                        showDialog(requireContext(), getString(R.string.board_delete_notice)) {
                            viewModel.deleteBoard(boardItem.boardNo, boardItem.writerId.toString())
                        }
                        true
                    }
                    R.id.button_board_update -> {
                        findNavController().navigate(R.id.action_boardDetailFragment_to_boardModifyFragment)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    fun writeComment(boardNo: String) {
        val comment = bindingNonNull.edittextComment.text.toString()
        if (comment == "") {
            showToast(getString(R.string.board_write_textview_content_hint))
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
                    if (it == getString(R.string.board_delete_notice_success)) findNavController().navigate(R.id.action_boardDetailFragment_to_boardListFragment)
                }
            }

            comment.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let { comment ->
                    if (comment == "success") {
                        viewModel.boardDetail.value?.boardDetail?.let { viewModel.getBoardDetail(it.boardNo) }
                        showToast(getString(R.string.detail_comment_notice_success))
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
                boardItem = board.boardDetail

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