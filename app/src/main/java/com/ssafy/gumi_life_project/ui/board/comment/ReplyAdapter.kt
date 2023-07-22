package com.ssafy.gumi_life_project.ui.board.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumi_life_project.data.model.Reply
import com.ssafy.gumi_life_project.databinding.ItemReplyBinding

class ReplyAdapter : ListAdapter<Reply, ReplyAdapter.ReplyViewHolder>(ReplyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyViewHolder {
        val binding = ItemReplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReplyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReplyViewHolder, position: Int) {
        val reply = getItem(position)
        holder.bind(reply)
    }

    class ReplyViewHolder(private val binding: ItemReplyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reply: Reply) {
            binding.reply = reply
        }
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
