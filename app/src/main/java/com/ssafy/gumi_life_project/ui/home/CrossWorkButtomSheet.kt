package com.ssafy.gumi_life_project.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.model.CrossWork
import com.ssafy.gumi_life_project.databinding.BottomSheetCrossWorkBinding

class CrossWorkBottomSheet(private val title: String, private val content: String) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetCrossWorkBinding
    private lateinit var adapter: CrossWorkTimeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetCrossWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val crossWorkTimeList = getCrossWorkTimeList()

        adapter = CrossWorkTimeListAdapter(crossWorkTimeList)
        binding.recyclerviewMemos.adapter = adapter

        binding.textviewCrossWorkTitle.text = title
        binding.textviewCrossWorkExplain.text = content
    }

    private fun getCrossWorkTimeList(): List<CrossWork> {
        val list = mutableListOf<CrossWork>()
        list.add(CrossWork("10:00 AM"))
        list.add(CrossWork("12:30 PM"))
        list.add(CrossWork("3:15 PM"))
        return list
    }

    override fun getTheme(): Int = R.style.BottomSheetDialog
}