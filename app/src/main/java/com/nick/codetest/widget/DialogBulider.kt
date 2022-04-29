package com.nick.codetest.widget

import android.app.Activity
import android.app.AlertDialog
import com.nick.codetest.R
import kotlinx.android.synthetic.main.progress_dialog_generic.*

class LoadingDialog(val activity: Activity) {
    private var builder = AlertDialog.Builder(activity)
    private var dialog: AlertDialog

    init {
        builder.setView(R.layout.progress_dialog_generic)
        builder.setCancelable(false)
        dialog = builder.create()
    }

    fun showWithTitle(title: String) {
        dialog.show()
        dialog.progress_textView.text = title
    }

    fun dismiss() {
        dialog.dismiss()
    }
}
