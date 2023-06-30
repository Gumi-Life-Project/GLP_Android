package com.ssafy.gumi_life_project.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.model.CrossWorkTime

class CrossWorkTimeListAdapter(private val crossWorkTimeList: List<CrossWorkTime>) :
    RecyclerView.Adapter<CrossWorkTimeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cross_work_time, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val crossWorkTime = crossWorkTimeList[position]
        holder.bind(crossWorkTime)
    }

    override fun getItemCount(): Int {
        return crossWorkTimeList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val timeTextView: TextView = itemView.findViewById(R.id.textview_time)

        fun bind(crossWorkTime: CrossWorkTime) {
            timeTextView.text = crossWorkTime.time
        }
    }
}