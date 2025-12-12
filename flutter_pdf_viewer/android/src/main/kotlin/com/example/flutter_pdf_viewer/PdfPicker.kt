package com.example.flutter_pdf_viewer

import android.app.Activity
import android.content.Intent
import android.content.Context
import android.net.Uri
import io.flutter.plugin.common.MethodChannel

object PdfPicker {

    private var pendingResult: MethodChannel.Result? = null

    fun pick(context: Context, result: MethodChannel.Result) {
        pendingResult = result

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        val activity = context as Activity
        activity.startActivityForResult(intent, 1001)
    }

    fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            val uri: Uri? = data?.data
            pendingResult?.success(uri.toString())
        } else {
            pendingResult?.success(null)
        }
        pendingResult = null
    }
}
