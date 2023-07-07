package com.ssafy.gumi_life_project.ui.home.crosswalk

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumi_life_project.databinding.ItemCrossWalkTimeBinding

class CrossWalkTimeListAdapter(private val crossWalkTimeList: List<String>) :
    RecyclerView.Adapter<CrossWalkTimeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCrossWalkTimeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val crossWorkTime = crossWalkTimeList[position]
        holder.bind(crossWorkTime)
    }

    override fun getItemCount(): Int {
        return crossWalkTimeList.size
    }

    inner class ViewHolder(private val binding: ItemCrossWalkTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(crossWalkTime: String) {
            binding.crossWalkTime = crossWalkTime
            binding.executePendingBindings()
        }
    }
}
