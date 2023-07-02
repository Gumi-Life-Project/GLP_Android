package com.ssafy.gumi_life_project.ui.home.crosswalk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.model.CrossWalk

class CrossWalkTimeListAdapter(private val crossWalkTimeList: List<CrossWalk>) :
    RecyclerView.Adapter<CrossWalkTimeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cross_walk_time, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val crossWorkTime = crossWalkTimeList[position]
        holder.bind(crossWorkTime)
    }

    override fun getItemCount(): Int {
        return crossWalkTimeList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val timeTextView: TextView = itemView.findViewById(R.id.textview_time)

        fun bind(crossWalkTime: CrossWalk) {
            timeTextView.text = crossWalkTime.time
        }
    }
}