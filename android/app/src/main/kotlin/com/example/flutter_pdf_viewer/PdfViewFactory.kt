package com.example.flutter_pdf_viewer

import android.content.Context
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.FrameLayout
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory
import io.flutter.plugin.common.StandardMessageCodec
import java.io.File

class PdfViewFactory(private val context: Context) :
    PlatformViewFactory(StandardMessageCodec.INSTANCE) {

    override fun create(context: Context?, viewId: Int, args: Any?): PlatformView {
        val params = args as? Map<String, Any>
        val filePath = params?.get("filePath") as? String
        return PdfView(context!!, filePath!!)
    }
}

class PdfView(private val context: Context, private val filePath: String) : PlatformView {
    private val container = ScrollView(context)
    private val imageView = ImageView(context)

    init {
        val layout = FrameLayout(context)
        layout.addView(imageView)
        container.addView(layout)
        loadPdf(filePath)
    }

    private fun loadPdf(path: String) {
        try {
            val file = File(path)
            val parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
            val pdfRenderer = PdfRenderer(parcelFileDescriptor)
            val page = pdfRenderer.openPage(0)
            val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            imageView.setImageBitmap(bitmap)
            page.close()
            pdfRenderer.close()
            parcelFileDescriptor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getView() = container
    override fun dispose() {}
}
