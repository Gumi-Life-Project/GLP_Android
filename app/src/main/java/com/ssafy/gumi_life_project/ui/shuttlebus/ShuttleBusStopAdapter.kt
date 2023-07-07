package com.ssafy.gumi_life_project.ui.shuttlebus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumi_life_project.data.model.ShuttleBusStop
import com.ssafy.gumi_life_project.databinding.ItemShuttleBusStopBinding

class ShuttleBusStopAdapter(private val shuttleBusStopList: List<ShuttleBusStop>) :
    RecyclerView.Adapter<ShuttleBusStopAdapter.ViewHolder>() {

    interface ItemClickListener {
        fun onBusStopClicked(
            shuttleBusStop: ShuttleBusStop
        )
    }

    lateinit var itemClickListener: ItemClickListener

    inner class ViewHolder(private val binding: ItemShuttleBusStopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShuttleBusStop) {
            binding.shuttleBusStop = item
            binding.imageViewMark.visibility = View.INVISIBLE
            if (item.isMarked) {
                binding.imageViewMark.visibility = View.VISIBLE
            }
            binding.root.setOnClickListener {
                itemClickListener.onBusStopClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemShuttleBusStopBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return shuttleBusStopList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(shuttleBusStopList[position])
    }
}