package com.ssafy.gumi_life_project.util

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.ui.home.crosswalk.CrossWalkTimeView

@BindingAdapter("app:timeText")
fun setTimeText(view: CrossWalkTimeView, time: String) {
    view.setTime(time)
}

@BindingAdapter("app:timeColor")
fun setTrafficLightColor(view: CrossWalkTimeView, @ColorRes colorRes: Int) {
    val color = ContextCompat.getColor(view.context, colorRes)
    view.setTimeColor(color)
}

@BindingAdapter("app:isRunning")
fun setImageResource(imageView: ImageView, isRunning: Boolean) {
    imageView.setImageResource(if (!isRunning) R.drawable.baseline_play_circle_24 else R.drawable.round_pause_circle_24)
}

@BindingAdapter("app:likesNum")
fun setHitCount(textView: TextView, likesNum: Int) {
    textView.text = likesNum.toString()
}

@BindingAdapter("app:state")
fun setHeartImageResource(imageView: ImageView, state: Int) {
    imageView.setImageResource(if (state == 0) R.drawable.icon__heart_ else R.drawable.icon_heart)
}