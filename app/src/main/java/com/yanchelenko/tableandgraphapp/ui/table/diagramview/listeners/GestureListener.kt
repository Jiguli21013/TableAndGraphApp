package com.yanchelenko.tableandgraphapp.ui.table.diagramview.listeners

import android.view.GestureDetector
import android.view.MotionEvent
import com.yanchelenko.tableandgraphapp.ui.table.diagramview.interfaces.GraphStateController

class GestureListener(
    private val stateController: GraphStateController
) : GestureDetector.SimpleOnGestureListener() {

    override fun onDoubleTap(e: MotionEvent): Boolean {
        stateController.resetGraph() // Сбрасываем график на исходное состояние
        return true
    }

    override fun onDown(event: MotionEvent): Boolean = true

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
        // Уменьшаем чувствительность смещения
        val sensitivity = 0.8f / stateController.scaleFactor.coerceAtLeast(0.1f)

        // Обновляем смещение
        stateController.offsetX -= distanceX * sensitivity
        stateController.offsetY -= distanceY * sensitivity

        // Применяем ограничения на смещения
        stateController.constrainOffsets()

        return true
    }
}
