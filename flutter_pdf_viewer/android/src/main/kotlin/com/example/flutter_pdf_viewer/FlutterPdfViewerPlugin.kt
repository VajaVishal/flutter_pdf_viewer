package com.example.flutter_pdf_viewer

import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodChannel

class FlutterPdfViewerPlugin : FlutterPlugin {

    private lateinit var channel: MethodChannel

    override fun onAttachedToEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {

        // Register File Picker channel
        channel = MethodChannel(binding.binaryMessenger, "file_picker_native")
        channel.setMethodCallHandler { call, result ->
            when (call.method) {
                "pickPdf" -> PdfPicker.pick(binding.applicationContext, result)
                else -> result.notImplemented()
            }
        }

        // Register PDF View
        binding
            .platformViewRegistry
            .registerViewFactory(
                "pdf_view",
                PdfViewFactory(binding.applicationContext)
            )
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {}
}
