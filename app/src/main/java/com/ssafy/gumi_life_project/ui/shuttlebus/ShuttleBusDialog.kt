package com.ssafy.gumi_life_project.ui.shuttlebus

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.data.model.ShuttleBusStop
import com.ssafy.gumi_life_project.databinding.DialogShuttleBusBinding

class ShuttleBusDialog(context: Context, private val shuttleBusStop: ShuttleBusStop) :
    DialogFragment() {
    private lateinit var binding: DialogShuttleBusBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogShuttleBusBinding.inflate(inflater, container, false)
        binding.shuttleBusStop = shuttleBusStop
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        try {
            val parentWidth = resources.displayMetrics.widthPixels
            val size = parentWidth - (parentWidth / 6)
            dialog?.window?.setLayout(size, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_all_corners)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }
}