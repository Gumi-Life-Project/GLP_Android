package com.ssafy.gumi_life_project.ui.board.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.local.AppPreferences
import com.ssafy.gumi_life_project.data.model.Comment
import com.ssafy.gumi_life_project.data.model.Reply
import com.ssafy.gumi_life_project.databinding.ItemReplyBinding

class ReplyAdapter : ListAdapter<Reply, ReplyAdapter.ReplyViewHolder>(ReplyDiffCallback()) {

    var onReplyDelete: ((Reply) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyViewHolder {
        val binding = ItemReplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReplyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReplyViewHolder, position: Int) {
        val reply = getItem(position)
        holder.bind(reply)
    }

    inner class ReplyViewHolder(private val binding: ItemReplyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reply: Reply) {
            binding.reply = reply

            binding.imageviewMenu.setOnClickListener {
                showPopupMenu(it, reply)
            }
        }
    }


    private fun showPopupMenu(anchorView: View, reply: Reply) {
        val popupMenu = PopupMenu(anchorView.context, anchorView)
        popupMenu.inflate(R.menu.menu_comment)

        val isCurrentUserAuthor = reply.writerId == AppPreferences.getUserId().toString()

        if (isCurrentUserAuthor) {
            popupMenu.menu.findItem(R.id.button_comment_notice).isVisible = false
        } else {
            popupMenu.menu.findItem(R.id.button_comment_delete).isVisible = false
        }

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.button_comment_notice -> {
                    true
                }
                R.id.button_comment_delete -> {
                    onReplyDelete?.invoke(reply)
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }
}

class ReplyDiffCallback : DiffUtil.ItemCallback<Reply>() {
    override fun areItemsTheSame(oldItem: Reply, newItem: Reply): Boolean {
        return oldItem.replyNo == newItem.replyNo
    }

    override fun areContentsTheSame(oldItem: Reply, newItem: Reply): Boolean {
        return oldItem == newItem
    }
}
