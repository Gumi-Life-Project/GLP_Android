package com.ssafy.gumi_life_project.ui.board.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumi_life_project.data.model.Comment
import com.ssafy.gumi_life_project.databinding.ItemCommentBinding

class CommentAdapter : ListAdapter<Comment, CommentAdapter.CommentViewHolder>(CommentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = getItem(position)
        holder.bind(comment)
    }

    class CommentViewHolder(private val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.comment = comment

            if(comment.replyList.isEmpty()) {
                binding.imageviewEnter.visibility = View.GONE
            } else {
                val replyAdapter = ReplyAdapter()
                binding.replyRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
                binding.replyRecyclerView.adapter = replyAdapter
                replyAdapter.submitList(comment.replyList)
            }
        }
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