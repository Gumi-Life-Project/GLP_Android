package com.ssafy.gumi_life_project.ui.shuttlebus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.model.ShuttleBusLine
import com.ssafy.gumi_life_project.databinding.FragmentShuttleBusBinding
import com.ssafy.gumi_life_project.util.template.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShuttleBusFragment : BaseFragment<FragmentShuttleBusBinding>(
    R.layout.fragment_shuttle_bus
) {
    private val viewModel by viewModels<ShuttleBusViewModel>()
    private lateinit var adapter: ShuttleBusLineAdapter
    private lateinit var busLineList: MutableList<ShuttleBusLine>

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentShuttleBusBinding {
        return FragmentShuttleBusBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        bindingNonNull.viewModel = viewModel
        initData()
        initRecyclerView()
        initObserver()
    }

    fun initData() {
        viewModel.getShuttleBusLineList()
    }

    private fun initRecyclerView() {
        busLineList = mutableListOf<ShuttleBusLine>()
        adapter = ShuttleBusLineAdapter(busLineList, childFragmentManager)
        adapter.itemClickListener = object : ShuttleBusLineAdapter.ItemClickListener {
            override fun onExpandButtonClicked(
                isItemOpen: Boolean,
                shuttleBusStopRecyclerView: RecyclerView,
                expandButton: ImageView
            ): Boolean {
                if (isItemOpen) {
                    shuttleBusStopRecyclerView.visibility = View.GONE
                    expandButton.rotation = 0f
                } else {
                    shuttleBusStopRecyclerView.visibility = View.VISIBLE
                    expandButton.rotation = 180f
                }
                return !isItemOpen
            }

        }
        binding?.recyclerviewShuttleBusLine?.adapter = adapter
    }

    private fun initObserver() {
        viewModel.shuttleBusLineList.observe(viewLifecycleOwner) {
            busLineList.clear()
            busLineList.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }
}