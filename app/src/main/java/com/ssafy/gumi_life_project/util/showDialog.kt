package com.ssafy.gumi_life_project.util

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.ssafy.gumi_life_project.databinding.DialogNoticeBinding

fun showDialog(context: Context, title: String, onOkClick: () -> Unit) {
    val dialogBinding: DialogNoticeBinding = DialogNoticeBinding.inflate(
        LayoutInflater.from(context)
    )
    val dialogBuilder = AlertDialog.Builder(context).setView(dialogBinding.root)

    val customDialog = dialogBuilder.create()

    customDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

    dialogBinding.textviewTitle.text = title

    dialogBinding.textviewCancel.setOnClickListener {
        customDialog.dismiss()
    }

    dialogBinding.textviewOk.setOnClickListener {
        customDialog.dismiss()
        onOkClick()
    }

    customDialog.show()
}