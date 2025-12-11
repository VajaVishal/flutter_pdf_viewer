package com.example.flutter_pdf_viewer

import android.content.Intent
import android.app.Activity
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {

    private val CHANNEL = "file_picker_channel"
    private val PICK_PDF_REQUEST = 1001
    private var pendingResult: MethodChannel.Result? = null

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        // 🔹 1. Register PDF view factory
        flutterEngine.platformViewsController
            .registry
            .registerViewFactory("pdf_view", PdfViewFactory(this))

        // 🔹 2. Register file picker channel
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
            .setMethodCallHandler { call, result ->
                if (call.method == "pickPdf") {
                    pendingResult = result
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "application/pdf"
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_REQUEST)
                } else {
                    result.notImplemented()
                }
            }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PDF_REQUEST && resultCode == Activity.RESULT_OK) {
            pendingResult?.success(data?.data.toString())
        } else if (requestCode == PICK_PDF_REQUEST) {
            pendingResult?.error("CANCELLED", "User cancelled file picker", null)
        }
        pendingResult = null
    }
}
