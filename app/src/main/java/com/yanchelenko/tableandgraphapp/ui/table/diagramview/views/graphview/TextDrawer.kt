package com.yanchelenko.tableandgraphapp.ui.table.diagramview.views.graphview

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.yanchelenko.tableandgraphapp.ui.table.diagramview.views.graphview.interfaces.ITextDrawer

class TextDrawer : ITextDrawer {
    override fun drawText(canvas: Canvas, x: Float, y: Float, text: String) {
        val textPaint = Paint().apply {
            color = Color.BLACK
            textSize = 20f
            textAlign = Paint.Align.CENTER
        }
        if (y < 0) {
            canvas.drawText(text, x, y + 35f, textPaint)
        } else {
            canvas.drawText(text, x, y - 25f, textPaint)
        }
    }
}
