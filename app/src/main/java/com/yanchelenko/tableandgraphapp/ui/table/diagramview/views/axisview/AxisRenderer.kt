package com.yanchelenko.tableandgraphapp.ui.table.diagramview.views.axisview

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.yanchelenko.tableandgraphapp.ui.models.PointUI
import com.yanchelenko.tableandgraphapp.ui.table.diagramview.interfaces.GraphStateController
import com.yanchelenko.tableandgraphapp.ui.table.diagramview.interfaces.Renderer
import com.yanchelenko.tableandgraphapp.ui.table.diagramview.views.axisview.interfaces.IAxisDrawer
import com.yanchelenko.tableandgraphapp.ui.table.diagramview.views.axisview.interfaces.IGridDrawer
import com.yanchelenko.tableandgraphapp.ui.table.diagramview.views.axisview.interfaces.ILabelDrawer

class AxisRenderer : Renderer {
    private val axisDrawer: IAxisDrawer
    private val labelDrawer: ILabelDrawer
    private val gridDrawer: IGridDrawer

    init {
        axisDrawer = AxisDrawer()
        labelDrawer = LabelDrawer()
        gridDrawer = GridDrawer()
    }

    override fun draw(canvas: Canvas, graphState: GraphStateController, cellSize: Float, points: List<PointUI>) {
        // Центр экрана с учетом смещения
        val centerX = graphState.viewWidth / 2f + graphState.offsetX
        val centerY = graphState.viewHeight / 2f + graphState.offsetY

        // Настройка кисти для осей
        val axisPaint = Paint().apply {
            color = Color.BLACK
            strokeWidth = 3f
            isAntiAlias = true
        }

        // Рисуем ось X
        val startX = centerX - graphState.graphWidth * cellSize
        val endX = centerX + graphState.graphWidth * cellSize
        axisDrawer.drawAxis(canvas, startX, centerY, endX, centerY, axisPaint)

        // Рисуем ось Y
        val startY = centerY - graphState.graphHeight * cellSize
        val endY = centerY + graphState.graphHeight * cellSize
        axisDrawer.drawAxis(canvas, centerX, startY, centerX, endY, axisPaint)

        // Настройка кисти для текста
        val textPaint = Paint().apply {
            color = Color.BLACK
            textSize = 20f
            textAlign = Paint.Align.CENTER
        }

        // Рисуем метки и значения на оси X
        for (i in -graphState.graphWidth.toInt()..graphState.graphWidth.toInt() step 10) {
            val x = centerX + (i * cellSize)
            val y = centerY + 30
            labelDrawer.drawLabel(canvas, x, y, i.toString(), textPaint)
        }

        // Рисуем метки и значения на оси Y
        for (i in -graphState.graphHeight.toInt()..graphState.graphHeight.toInt() step 10) {
            val x = centerX + 30
            val y = centerY - (i * cellSize)
            labelDrawer.drawLabel(canvas, x, y, i.toString(), textPaint)
        }

        // Настройка кисти для сетки
        val gridPaint = Paint().apply {
            color = Color.LTGRAY
            strokeWidth = 2f
            isAntiAlias = true
        }

        // Рисуем вертикальные линии
        for (i in -graphState.graphWidth.toInt()..graphState.graphWidth.toInt() step 10) {
            val x = centerX + (i * cellSize)
            gridDrawer.drawGridLine(canvas, x, startY, x, endY, gridPaint)
        }

        // Рисуем горизонтальные линии
        for (i in -graphState.graphHeight.toInt()..graphState.graphHeight.toInt() step 10) {
            val y = centerY - (i * cellSize)
            gridDrawer.drawGridLine(canvas, startX, y, endX, y, gridPaint)
        }
    }
}
