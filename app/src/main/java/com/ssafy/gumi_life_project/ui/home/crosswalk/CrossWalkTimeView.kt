package com.ssafy.gumi_life_project.ui.home.crosswalk

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.databinding.ViewCrossWalkTimeBinding

class CrossWalkTimeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewCrossWalkTimeBinding = ViewCrossWalkTimeBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CrossWalkTimeView)
        val timeText = typedArray.getString(R.styleable.CrossWalkTimeView_timeText)
        typedArray.recycle()

        binding.apply {
            textviewTime.text = timeText ?: "00:00"
        }
    }

    fun setTime(time: String) {
        binding.textviewTime.text = time
    }
}