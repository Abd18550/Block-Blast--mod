package com.example.blockblastassistant.overlay

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.util.AttributeSet
import android.view.View
import com.example.blockblastassistant.solver.BoardState
import com.example.blockblastassistant.solver.Piece
import com.example.blockblastassistant.solver.Solver
import java.util.concurrent.Executors

class OverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var projection: MediaProjection? = null
    private var imageReader: ImageReader? = null

    private val paintRect = Paint().apply {
        color = Color.argb(120, 0, 255, 0)
        style = Paint.Style.FILL
    }
    private val paintStroke = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }

    private val executor = Executors.newSingleThreadExecutor()

    // Calibration (simple): whole screen assumed; user can adapt coords later
    var gridLeft = 0
    var gridTop = 0
    var cell = 40

    fun attachProjection(proj: MediaProjection) {
        projection = proj
        // TODO: set up virtual display + imagereader to capture frames
        // For now, we just invalidate periodically
        postInvalidateOnAnimation()
    }

    fun detach() {
        imageReader?.close()
        projection?.stop()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Example visualization: draw an 8x8 grid overlay
        for (r in 0 until 8) {
            for (c in 0 until 8) {
                val left = gridLeft + c * cell
                val top = gridTop + r * cell
                canvas.drawRect(
                    left.toFloat(), top.toFloat(),
                    (left + cell).toFloat(), (top + cell).toFloat(),
                    paintStroke
                )
            }
        }
        // Placeholder best move highlight
        canvas.drawRect(
            gridLeft.toFloat(), gridTop.toFloat(),
            (gridLeft + cell).toFloat(), (gridTop + cell).toFloat(),
            paintRect
        )
    }
}
