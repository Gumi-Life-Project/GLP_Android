package com.ssafy.gumi_life_project.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.databinding.BottomSheetTipBinding


class TipBottomSheet(private val subject: String?, private val description: String?) :
    BottomSheetDialogFragment() {
    lateinit var binding: BottomSheetTipBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetTipBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textviewTipSubject.text = subject
        binding.textviewTipDescription.text = description
    }

    override fun getTheme(): Int = R.style.BottomSheetDialog
}