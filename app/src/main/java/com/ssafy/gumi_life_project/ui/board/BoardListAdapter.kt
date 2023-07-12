package com.ssafy.gumi_life_project.ui.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumi_life_project.data.model.BoardItem
import com.ssafy.gumi_life_project.databinding.ItemBoardBinding

class BoardListAdapter(private val boardItems: MutableList<BoardItem>, private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(boardItem: BoardItem)
    }

    inner class ViewHolder(val binding: ItemBoardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BoardItem) {
            binding.board = item

            itemView.setOnClickListener {
                onItemClickListener.onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder)
            holder.bind(boardItems[position])
    }

    override fun getItemCount(): Int {
        return boardItems.size
    }
}