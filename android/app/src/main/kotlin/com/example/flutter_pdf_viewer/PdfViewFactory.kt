package com.example.flutter_pdf_viewer

import android.content.Context
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

class PdfViewFactory(private val context: Context) :
    PlatformViewFactory(StandardMessageCodec.INSTANCE) {

    override fun create(context: Context, viewId: Int, args: Any?): PlatformView {
        val params = args as Map<String, Any>
        return PdfViewController(context, params["filePath"] as String)
    }
}
