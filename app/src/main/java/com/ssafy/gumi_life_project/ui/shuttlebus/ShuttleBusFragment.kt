package com.ssafy.gumi_life_project.ui.shuttlebus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.model.ShuttleBusLine
import com.ssafy.gumi_life_project.data.model.ShuttleBusStop
import com.ssafy.gumi_life_project.databinding.FragmentShuttleBusBinding
import com.ssafy.gumi_life_project.util.template.BaseFragment

class ShuttleBusFragment : BaseFragment<FragmentShuttleBusBinding>(
    R.layout.fragment_shuttle_bus
) {
    private val viewModel by viewModels<ShuttleBusViewModel>()
    private lateinit var adapter: ShuttleBusLineAdapter

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentShuttleBusBinding {
        return FragmentShuttleBusBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        binding?.viewModel = viewModel
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val list_line = mutableListOf<ShuttleBusLine>()

        val list_stop = mutableListOf<ShuttleBusStop>()
        list_stop.add(ShuttleBusStop("구미1", 1.1, 1.1, "07:10", false))
        list_stop.add(ShuttleBusStop("구미2", 1.1, 1.1, "07:25", true))
        list_stop.add(ShuttleBusStop("구미3", 1.1, 1.1, "07:40", false))
        list_stop.add(ShuttleBusStop("구미4", 1.1, 1.1, "07:50", false))
        list_line.add(ShuttleBusLine("구미 노선", list_stop))

        val list_stop2 = mutableListOf<ShuttleBusStop>()
        list_stop2.add(ShuttleBusStop("칠곡1", 1.1, 1.1, "07:10", false))
        list_stop2.add(ShuttleBusStop("칠곡2", 1.1, 1.1, "07:25", false))
        list_stop2.add(ShuttleBusStop("칠곡3", 1.1, 1.1, "07:40", false))
        list_stop2.add(ShuttleBusStop("칠곡4", 1.1, 1.1, "07:50", false))
        list_line.add(ShuttleBusLine("칠곡 노선", list_stop2))

        adapter = ShuttleBusLineAdapter(list_line, childFragmentManager)
        adapter.itemClickListener = object : ShuttleBusLineAdapter.ItemClickListener {
            override fun onExpandButtonClicked(
                isItemOpen: Boolean,
                shuttleBusStoprecyclerView: RecyclerView,
                expandButton: ImageView
            ): Boolean {
                if (isItemOpen) {
                    shuttleBusStoprecyclerView.visibility = View.GONE
                } else {
                    shuttleBusStoprecyclerView.visibility = View.VISIBLE
                }
                return !isItemOpen
            }

        }
        binding?.recyclerviewShuttleBusLine?.adapter = adapter
    }
}