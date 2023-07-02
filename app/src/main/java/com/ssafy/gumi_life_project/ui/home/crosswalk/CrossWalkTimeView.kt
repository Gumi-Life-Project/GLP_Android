package com.ssafy.gumi_life_project.ui.home.crosswalk

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.databinding.ViewCrossworkTimeBinding

class CrossWalkTimeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewCrossworkTimeBinding = ViewCrossworkTimeBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CrossWalkTimeView)
        val timeText = typedArray.getString(R.styleable.CrossWorkTimeView_timeText)
        typedArray.recycle()

        binding.apply {
            textviewTime.text = timeText ?: "00:00"
        }
    }
}