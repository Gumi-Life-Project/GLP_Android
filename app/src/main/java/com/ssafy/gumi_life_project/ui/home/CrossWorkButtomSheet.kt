package com.ssafy.gumi_life_project.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.model.CrossWorkTime

class CrossWorkBottomSheet : BottomSheetDialogFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CrossWorkTimeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_cross_work, container, false)
        recyclerView = view.findViewById(R.id.recyclerview_memos)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val crossWorkTimeList = getCrossWorkTimeList()

        adapter = CrossWorkTimeListAdapter(crossWorkTimeList)
        recyclerView.adapter = adapter
    }

    private fun getCrossWorkTimeList(): List<CrossWorkTime> {
        val list = mutableListOf<CrossWorkTime>()
        list.add(CrossWorkTime("10:00 AM"))
        list.add(CrossWorkTime("12:30 PM"))
        list.add(CrossWorkTime("3:15 PM"))
        return list
    }
}
