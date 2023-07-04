package com.ssafy.gumi_life_project.util

import androidx.databinding.BindingAdapter
import com.ssafy.gumi_life_project.ui.home.crosswalk.CrossWalkTimeView

@BindingAdapter("app:timeText")
fun setTimeText(view: CrossWalkTimeView, time: String) {
    view.setTime(time)
}