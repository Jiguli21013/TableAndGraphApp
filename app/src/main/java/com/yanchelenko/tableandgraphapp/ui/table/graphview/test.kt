package com.yanchelenko.tableandgraphapp.ui.table.graphview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import com.yanchelenko.tableandgraphapp.ui.models.PointUI

class GridTableViewt @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var scaleFactor = 1f // Начальный масштаб
    private val scaleDetector = ScaleGestureDetector(context, ScaleListener())
    private var offsetX = 0f
    private var offsetY = 0f
    private val gestureDetector = GestureDetector(context, GestureListener())

    var cellSize = minOf(width, height) / 160f
    private val points = mutableListOf<PointUI>()
    private val graphWidth = 250f
    private val graphHeight = 250f

    // Paints
    private val pointPaint = Paint().apply {
        color = Color.RED
        strokeWidth = 14f
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private val axisPaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 3f
        isAntiAlias = true
    }

    private val linePaint = Paint().apply {
        color = Color.GREEN
        strokeWidth = 4f
        isAntiAlias = true
        style = Paint.Style.STROKE
    }

    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 20f
        textAlign = Paint.Align.CENTER
    }

    // Draw points and lines
    private fun drawPointsAndLines(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f
        val path = Path()

        for ((index, point) in points.withIndex()) {
            val x = centerX + (point.x * cellSize) + offsetX
            val y = centerY - (point.y * cellSize) + offsetY

            // Draw point
            canvas.drawCircle(x, y, 5f, pointPaint)

            // Draw text
            val text = "(${point.x.toInt()}, ${point.y.toInt()})"
            val textOffset = if (point.y < 0) 35f else -25f
            canvas.drawText(text, x, y + textOffset, textPaint)

            // Draw path
            if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        canvas.drawPath(path, linePaint)
    }

    // Draw axes and labels
    private fun drawAxesAndLabels(canvas: Canvas) {
        val centerX = width / 2f + offsetX
        val centerY = height / 2f + offsetY

        // Draw axes
        canvas.drawLine(centerX - graphWidth * cellSize, centerY, centerX + graphWidth * cellSize, centerY, axisPaint)
        canvas.drawLine(centerX, centerY - graphHeight * cellSize, centerX, centerY + graphHeight * cellSize, axisPaint)

        // Draw grid lines
        val lineGridPaint = Paint().apply {
            color = Color.LTGRAY
            strokeWidth = 2f
            isAntiAlias = true
        }

        for (i in -graphWidth.toInt()..graphWidth.toInt() step 10) {
            val x = centerX + (i * cellSize)
            canvas.drawLine(x, centerY - graphHeight * cellSize, x, centerY + graphHeight * cellSize, lineGridPaint)
        }
        for (i in -graphHeight.toInt()..graphHeight.toInt() step 10) {
            val y = centerY - (i * cellSize)
            canvas.drawLine(centerX - graphWidth * cellSize, y, centerX + graphWidth * cellSize, y, lineGridPaint)
        }

        // Draw labels
        for (i in -graphWidth.toInt()..graphWidth.toInt() step 10) {
            canvas.drawText(i.toString(), centerX + (i * cellSize), centerY + 30, textPaint)
        }
        for (i in -graphHeight.toInt()..graphHeight.toInt() step 10) {
            canvas.drawText(i.toString(), centerX + 30, centerY - (i * cellSize), textPaint)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()

        canvas.scale(scaleFactor, scaleFactor, width / 2f, height / 2f)
        canvas.translate(offsetX / scaleFactor, offsetY / scaleFactor)

        drawAxesAndLabels(canvas)
        drawPointsAndLines(canvas)

        canvas.restore()
    }

    private fun resetGraphToInitialState() {
        scaleFactor = 1f
        offsetX = 0f
        offsetY = 0f
        invalidate()
    }

    // Zoom functionality
    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)
        return true
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val newScaleFactor = (scaleFactor * detector.scaleFactor).coerceIn(0.5f, 3f)
            val scaleDelta = newScaleFactor / scaleFactor
            scaleFactor = newScaleFactor

            val focusX = detector.focusX
            val focusY = detector.focusY

            // Apply smoothing for offsets
            val smoothingFactor = 0.5f
            offsetX += (focusX - width / 2f - offsetX) * (1 - scaleDelta) * smoothingFactor
            offsetY += (focusY - height / 2f - offsetY) * (1 - scaleDelta) * smoothingFactor

            invalidate()
            return true
        }
    }

    // Gesture functionality
    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            resetGraphToInitialState()
            return true
        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            val sensitivity = 0.8f / scaleFactor.coerceAtLeast(0.1f)
            offsetX -= distanceX * sensitivity
            offsetY -= distanceY * sensitivity

            val graphWidthInPixels = (graphWidth * cellSize * scaleFactor).coerceAtLeast(1f)
            val graphHeightInPixels = (graphHeight * cellSize * scaleFactor).coerceAtLeast(1f)

            val screenWidth = width.toFloat()
            val screenHeight = height.toFloat()

            var minX = -(graphWidthInPixels / 2) + (screenWidth / 2)
            var maxX = (graphWidthInPixels / 2) - (screenWidth / 2)
            if (minX > maxX) {
                val temp = minX
                minX = maxX
                maxX = temp
            }

            var minY = -(graphHeightInPixels / 2) + (screenHeight / 2)
            var maxY = (graphHeightInPixels / 2) - (screenHeight / 2)
            if (minY > maxY) {
                val temp = minY
                minY = maxY
                maxY = temp
            }

            offsetX = offsetX.coerceIn(minX, maxX)
            offsetY = offsetY.coerceIn(minY, maxY)

            invalidate()
            return true
        }
    }

    // Method to update the data
    fun setData(newData: List<PointUI>) {
        points.clear()
        points.addAll(newData)
        invalidate()
    }

    companion object {
        // Add constants here
    }
}
