package com.yanchelenko.tableandgraphapp.ui.table.diagramview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import com.yanchelenko.tableandgraphapp.ui.models.ListOfPointsUI
import com.yanchelenko.tableandgraphapp.ui.models.PointUI
import com.yanchelenko.tableandgraphapp.ui.table.diagramview.interfaces.GraphStateController
import com.yanchelenko.tableandgraphapp.ui.table.diagramview.interfaces.Renderer
import com.yanchelenko.tableandgraphapp.ui.table.diagramview.listeners.GestureListener
import com.yanchelenko.tableandgraphapp.ui.table.diagramview.listeners.ScaleListener
import com.yanchelenko.tableandgraphapp.ui.table.diagramview.views.axisview.AxisRenderer
import com.yanchelenko.tableandgraphapp.ui.table.diagramview.views.graphview.GraphRenderer

class DiagramView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), GraphStateController {

    private val points = mutableListOf<PointUI>()

    // Смещение
    override var offsetX = 0f
    override var offsetY = 0f

    // Масштаб
    override var scaleFactor = 1f

    override var graphWidth = 250f // Максимальные значения для x //todo при мапе в PointUI брать значение
    override var graphHeight = 250f // Максимальные значения для y //todo при мапе в PointUI брать значение

    // Рассчитываем размер ячейки для отображения точек (та же логика, что и для рисования линий)
    override val cellSize get() = minOf(width, height) / 160f
    override val viewWidth get() = width.toFloat()
    override val viewHeight get() = height.toFloat()

    private val renderers = listOf<Renderer>(AxisRenderer(), GraphRenderer()) // Добавляем рендереры

    private val gestureDetector = GestureDetector(context, GestureListener(this))
    private val scaleDetector: ScaleGestureDetector = ScaleGestureDetector(context, ScaleListener(this))

    override fun resetGraph() {
        offsetX = 0f
        offsetY = 0f
        scaleFactor = 1f
        invalidate()
    }

    override fun constrainOffsets() {
        val graphWidthInPixels = (graphWidth * cellSize * scaleFactor).coerceAtLeast(1f)
        val graphHeightInPixels = (graphHeight * cellSize * scaleFactor).coerceAtLeast(1f)

        var minX = -(graphWidthInPixels / 2) + (viewWidth / 2)
        var maxX = (graphWidthInPixels / 2) - (viewWidth / 2)

        if (minX > maxX) minX = maxX.also { maxX = minX }

        var minY = -(graphHeightInPixels / 2) + (viewHeight / 2)
        var maxY = (graphHeightInPixels / 2) - (viewHeight / 2)

        if (minY > maxY) minY = maxY.also { maxY = minY }

        offsetX = offsetX.coerceIn(minX, maxX)
        offsetY = offsetY.coerceIn(minY, maxY)
        invalidate()
    }

    override fun updateScale(focusX: Float, focusY: Float, scaleDelta: Float) {
        val smoothingFactor = 0.5f
        offsetX += (focusX - width / 2f - offsetX) * (1 - scaleDelta) * smoothingFactor
        offsetY += (focusY - height / 2f - offsetY) * (1 - scaleDelta) * smoothingFactor
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Сохраняем состояние канвы перед трансформациями
        canvas.save()

        // Применяем масштаб и смещение
        canvas.scale(scaleFactor, scaleFactor, width / 2f, height / 2f)
        canvas.translate(offsetX / scaleFactor, offsetY / scaleFactor)

        // Ваши действия по рисованию
        renderers.forEach { it.draw(canvas, this, cellSize, points) }

        // Восстанавливаем состояние канвы
        canvas.restore()
    }

    /** Zoom functionality */

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event) // Обрабатываем жесты масштабирования
        gestureDetector.onTouchEvent(event) // Передаем событие в GestureDetector
        return true
    }

    override fun invalidateView() {
        invalidate()
    }


    //todo как лучше прокидывать данные в эту вью
    fun setData(newData: ListOfPointsUI) {
        points.clear()
        points.addAll(newData.points)
        invalidate()
    }

    companion object {
        const val DEFAULT_CELL_SIZE_DIVISOR = 160f
        const val DEFAULT_GRAPH_WIDTH = 250f
        const val DEFAULT_GRAPH_HEIGHT = 250f
    }
}
