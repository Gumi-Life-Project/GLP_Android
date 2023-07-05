package com.ssafy.gumi_life_project.ui.home.crosswalk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.databinding.BottomSheetCrossWalkBinding
import com.ssafy.gumi_life_project.util.CrossWorkTimeList
import com.ssafy.gumi_life_project.util.getCrossWorkTimeListWithRecentTime
import java.text.SimpleDateFormat
import java.util.*

class CrossWalkBottomSheet(private val title: String, private val content: String) :
    BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetCrossWalkBinding
    private lateinit var adapter: CrossWalkTimeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetCrossWalkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val crossWorkTimeList = getCrossWorkTimeListWithRecentTime(getCurrentTime())

        adapter = CrossWalkTimeListAdapter(crossWorkTimeList)
        binding.recyclerviewTimes.adapter = adapter
        binding.textviewCrossWorkTitle.text = title
        binding.textviewCrossWorkExplain.text = content
    }

    private fun getCurrentTime(): String {
        val currentTime = Calendar.getInstance().time
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        return format.format(currentTime)
    }

    override fun getTheme(): Int = R.style.BottomSheetDialog
}