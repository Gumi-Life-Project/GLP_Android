package com.ssafy.gumi_life_project.ui.home.crosswalk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.model.SignalLight
import com.ssafy.gumi_life_project.databinding.BottomSheetCrossWalkBinding
import com.ssafy.gumi_life_project.util.getCrossWorkTimeListWithRecentTime
import java.util.*

class CrossWalkBottomSheet(
    private val signalLight: SignalLight,
    private val title: String,
    private val content: String
) :
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

        val signalLightType = if(isAfternoon()) {
            when(signalLight) {
                SignalLight.SIGNAL_LIGHT_1 -> SignalLight.SIGNAL_LIGHT_4
                SignalLight.SIGNAL_LIGHT_2 -> SignalLight.SIGNAL_LIGHT_5
                SignalLight.SIGNAL_LIGHT_3 -> SignalLight.SIGNAL_LIGHT_6
                else -> signalLight
            }
        } else {
            signalLight
        }

        val crossWorkTimeList =
            getCrossWorkTimeListWithRecentTime(signalLightType, getCurrentTimeInSeconds())

        adapter = CrossWalkTimeListAdapter(crossWorkTimeList)
        binding.recyclerviewTimes.adapter = adapter
        binding.textviewCrossWorkTitle.text = title
        binding.textviewCrossWorkExplain.text = content
    }

    private fun isAfternoon(): Boolean {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        return currentHour >= 12
    }

    private fun getCurrentTimeInSeconds(): Int {
        val currentTime = Calendar.getInstance()
        return currentTime.get(Calendar.HOUR_OF_DAY) * 3600 +
                currentTime.get(Calendar.MINUTE) * 60 +
                currentTime.get(Calendar.SECOND)
    }

    override fun getTheme(): Int = R.style.BottomSheetDialog
}