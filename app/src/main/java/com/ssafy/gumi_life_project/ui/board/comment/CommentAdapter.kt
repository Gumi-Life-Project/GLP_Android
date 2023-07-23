package com.ssafy.gumi_life_project.ui.board.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.local.AppPreferences
import com.ssafy.gumi_life_project.data.model.Comment
import com.ssafy.gumi_life_project.data.model.Reply
import com.ssafy.gumi_life_project.databinding.ItemCommentBinding

class CommentAdapter :
    ListAdapter<Comment, CommentAdapter.CommentViewHolder>(CommentDiffCallback()) {

    private var currentClickedPosition: Int? = null
    var onCommentClick: ((Comment) -> Unit)? = null
    var onCommentDelete: ((Comment) -> Unit)? = null
    var onReplyDelete: ((Reply) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = getItem(position)
        holder.bind(comment)

        if (currentClickedPosition == position) {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.board_light_blue
                )
            )
        } else {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            )
        }
    }

    fun changeCommentColor() {
        currentClickedPosition = null
        notifyDataSetChanged()
    }

    inner class CommentViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.comment = comment

            binding.imageviewReply.setOnClickListener {
                onCommentClick?.invoke(comment)

                currentClickedPosition = if (adapterPosition == currentClickedPosition) {
                    null
                } else {
                    adapterPosition
                }

                notifyDataSetChanged()
            }

            binding.imageviewMenu.setOnClickListener {
                showPopupMenu(it, comment)
            }

            if (comment.replyList.isEmpty()) {
                binding.imageviewEnter.visibility = View.GONE
            } else {
                val replyAdapter = ReplyAdapter()
                binding.replyRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
                binding.replyRecyclerView.adapter = replyAdapter
                replyAdapter.onReplyDelete = {
                    onReplyDelete?.invoke(it)
                }
                replyAdapter.submitList(comment.replyList)
            }
        }
    }

    private fun showPopupMenu(anchorView: View, comment: Comment) {
        val popupMenu = PopupMenu(anchorView.context, anchorView)
        popupMenu.inflate(R.menu.menu_board)

        val isCurrentUserAuthor = comment.writerId == AppPreferences.getUserId().toString()

        if (isCurrentUserAuthor) {
            popupMenu.menu.findItem(R.id.button_board_notice).isVisible = false
        } else {
            popupMenu.menu.findItem(R.id.button_board_update).isVisible = false
            popupMenu.menu.findItem(R.id.button_board_delete).isVisible = false
        }

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.button_board_notice -> {
                    true
                }
                R.id.button_board_update -> {
                    true
                }
                R.id.button_board_delete -> {
                    onCommentDelete?.invoke(comment)
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }
}

class CommentDiffCallback : DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.commentNo == newItem.commentNo
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem == newItem
    }
}