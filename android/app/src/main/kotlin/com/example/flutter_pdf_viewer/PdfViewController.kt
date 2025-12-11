package com.example.flutter_pdf_viewer

import android.content.Context
import android.graphics.*
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.FrameLayout
import androidx.core.net.toUri
import io.flutter.plugin.platform.PlatformView

class PdfViewController(
    private val ctx: Context,
    private val filePath: String
) : PlatformView {

    private val container = FrameLayout(ctx)
    private val pdfView = ZoomPdfView(ctx, filePath)

    init {
        container.addView(pdfView)
    }

    override fun getView(): View = container
    override fun dispose() {}
}


// -------------------------------
//      CUSTOM ZOOM PDF VIEW
// -------------------------------

class ZoomPdfView(context: Context, private val filePath: String) : View(context) {

    private var bitmap: Bitmap? = null

    private var scale = 1f
    private var minScale = 1f
    private var maxScale = 6f

    private var translateX = 0f
    private var translateY = 0f

    private val matrix = Matrix()

    private val scaleDetector = ScaleGestureDetector(context,
        object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                scale *= detector.scaleFactor
                scale = scale.coerceIn(minScale, maxScale)
                invalidate()
                return true
            }
        })

    private val gestureDetector = GestureDetector(context,
        object : GestureDetector.SimpleOnGestureListener() {

            override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                translateX -= distanceX
                translateY -= distanceY
                invalidate()
                return true
            }
        })

    init {
        loadPdf()
    }

    private fun loadPdf() {
        val fd: ParcelFileDescriptor =
            context.contentResolver.openFileDescriptor(filePath.toUri(), "r") ?: return

        val renderer = PdfRenderer(fd)
        val page = renderer.openPage(0)

        bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
        page.render(bitmap!!, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

        page.close()
        renderer.close()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        bitmap?.let {
            matrix.reset()
            matrix.postScale(scale, scale)
            matrix.postTranslate(translateX, translateY)
            canvas.drawBitmap(it, matrix, null)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)
        return true
    }
}
