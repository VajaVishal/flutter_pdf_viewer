package com.example.flutter_pdf_viewer

import android.app.Activity
import android.content.Intent
import android.net.Uri
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {

    private val CHANNEL = "file_picker_native"
    private var resultCallback: MethodChannel.Result? = null

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
            .setMethodCallHandler { call, result ->
                if (call.method == "pickPdf") {
                    pickPdf(result)
                } else result.notImplemented()
            }

        flutterEngine
            .platformViewsController
            .registry
            .registerViewFactory("pdf_view", PdfViewFactory(this))
    }

    private fun pickPdf(result: MethodChannel.Result) {
        resultCallback = result

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        startActivityForResult(intent, 1001)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1001 && resultCode == Activity.RESULT_OK)
            resultCallback?.success(data?.data?.toString())
        else
            resultCallback?.success(null)

        resultCallback = null
    }
}
