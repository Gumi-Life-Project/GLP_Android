package com.ssafy.gumi_life_project.ui.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.gumi_life_project.R
import com.ssafy.gumi_life_project.databinding.BottomSheetReportBinding
import com.ssafy.gumi_life_project.util.showDialog

class ReportBottomSheet() : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetReportBinding
    private val viewModel by activityViewModels<BoardViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        binding.buttonReportHate.setOnClickListener {
            viewModel.setReportType(getString(R.string.report_type_hate))
            showDialog()
        }
        binding.buttonReportCheat.setOnClickListener {
            viewModel.setReportType(getString(R.string.report_type_cheat))
            showDialog()
        }
        binding.buttonReportPolitical.setOnClickListener {
            viewModel.setReportType(getString(R.string.report_type_political))
            showDialog()
        }
        binding.buttonReportImpersonation.setOnClickListener {
            viewModel.setReportType(getString(R.string.report_type_impersonation))
            showDialog()
        }
        binding.buttonReportPornography.setOnClickListener {
            viewModel.setReportType(getString(R.string.report_type_pornography))
            showDialog()
        }
        binding.buttonReportCommercial.setOnClickListener {
            viewModel.setReportType(getString(R.string.report_type_commercial))
            showDialog()
        }
        binding.buttonReportInappropriateness.setOnClickListener {
            viewModel.setReportType(getString(R.string.report_type_inappropriateness))
            showDialog()
        }
    }

    private fun showDialog() {
        showDialog(requireContext(), getString(R.string.report_check), getString(R.string.ok)) {
            viewModel.report()
        }
    }
}