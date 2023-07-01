package com.ssafy.gumi_life_project.ui.shuttlebus

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.model.ShuttleBusLine
import com.ssafy.gumi_life_project.data.model.ShuttleBusStop
import com.ssafy.gumi_life_project.databinding.DialogShuttleBusBinding
import com.ssafy.gumi_life_project.databinding.ItemShuttleBusLineBinding

class ShuttleBusLineAdapter(
    private val shuttleBusLineList: List<ShuttleBusLine>,
    private val fragmentManager: FragmentManager
) :
    RecyclerView.Adapter<ShuttleBusLineAdapter.ViewHolder>() {

    interface ItemClickListener {
        fun onExpandButtonClicked(
            isItemOpen: Boolean,
            shuttleBusStoprecyclerView: RecyclerView,
            expandButton : ImageView
        ): Boolean
    }

    lateinit var itemClickListener: ItemClickListener

    private lateinit var shuttleBusStopAdapter: ShuttleBusStopAdapter

    inner class ViewHolder(private val binding: ItemShuttleBusLineBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var isItemOpen: Boolean = false

        fun bind(item: ShuttleBusLine) {
            binding.shuttleBusLine = item
            shuttleBusStopAdapter = ShuttleBusStopAdapter(item.stopList)
            shuttleBusStopAdapter.itemClickListener =
                object : ShuttleBusStopAdapter.ItemClickListener {
                    override fun onitemClicked(shuttleBusStop: ShuttleBusStop) {
                    }
                }
            binding.recyclerviewShuttleBusStop.adapter = shuttleBusStopAdapter
            binding.buttonExpand
            binding.recyclerviewShuttleBusStop.visibility = View.GONE
            binding.buttonExpand.setOnClickListener {
                isItemOpen = itemClickListener.onExpandButtonClicked(
                    isItemOpen,
                    binding.recyclerviewShuttleBusStop,
                    binding.buttonExpand
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemShuttleBusLineBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return shuttleBusLineList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(shuttleBusLineList[position])
    }
}