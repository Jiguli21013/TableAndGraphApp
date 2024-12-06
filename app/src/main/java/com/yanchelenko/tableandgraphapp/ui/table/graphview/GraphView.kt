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

class GridTableView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var canvasTest: Canvas? = null

    //zoom
    private var scaleFactor = 1f // Начальный масштаб
    private val scaleDetector: ScaleGestureDetector = ScaleGestureDetector(context, ScaleListener())

    //перемещение
    private var offsetX = 0f
    private var offsetY = 0f

    private val gestureDetector = GestureDetector(context, GestureListener())

    // Рассчитываем размер ячейки для отображения точек (та же логика, что и для рисования линий)
    var cellSize = minOf(width, height) / 160f //todo придумать что-то с 160f (magic number)

    val points = mutableListOf<PointUI>()

    val graphWidth = 250f  // Максимальные значения для x //todo при мапе в PointUI брать значение
    val graphHeight = 250f // Максимальные значения для y //todo при мапе в PointUI брать значение

    //todo подумать над плавными линиями...
    private fun drawPointsAndLines(canvas: Canvas) {
        // Центр экрана относительно размеров View
        val centerX = width / 2f
        val centerY = height / 2f

        // Создаем Path для соединения точек
        val path = Path()

        // Преобразуем координаты точек в пиксели на экране
        for ((index, point) in points.withIndex()) {
            // Преобразуем значения в пиксели
            val x = centerX + (point.x * cellSize) + offsetX
            val y = centerY - (point.y * cellSize) + offsetY // Инвертируем Y, чтобы (0, 0) было в центре

            // Рисуем точку
            //todo вынести
            val pointPaint = Paint().apply {
                color = Color.RED
                strokeWidth = 14f
                isAntiAlias = true
                style = Paint.Style.FILL
            }
            canvas.drawCircle(x, y, 5f, pointPaint)
            //todo вынести
            val textPaint = Paint().apply {
                color = Color.BLACK
                textSize = 20f // Размер текста
                textAlign = Paint.Align.CENTER // Центрируем текст
            }

            // Рисуем текст с координатами //todo
            val text = "(${point.x.toInt()}, ${point.y.toInt()})"
            if (point.y < 0) {
                canvas.drawText(text, x, y + 35f, textPaint) // Смещаем текст выше точки
            } else {
                canvas.drawText(text, x, y - 25f, textPaint) // Смещаем текст выше точки
            }
            // Если это не первая точка, соединяем ее с предыдущей
            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }
        //todo вынести
        val linePaint = Paint().apply {
            color = Color.GREEN
            strokeWidth = 4f
            isAntiAlias = true
            style = Paint.Style.STROKE
        }


        // Рисуем линии между точками
        canvas.drawPath(path, linePaint)
    }

    private fun drawAxesAndLabels(canvas: Canvas) {
        // Центр экрана с учетом смещения
        val centerX = width / 2f + offsetX
        val centerY = height / 2f + offsetY

        //todo вынести

        // Настройка кисти для осей
        val axisPaint = Paint().apply {
            color = Color.BLACK
            strokeWidth = 3f
            isAntiAlias = true
        }

        // Рисуем ось X (горизонтальная линия)
        val startX = centerX - graphWidth * cellSize // Начало оси X с учетом масштаба
        val endX = centerX + graphWidth * cellSize // Конец оси X с учетом масштаба
        canvas.drawLine(startX, centerY, endX, centerY, axisPaint)

        // Рисуем ось Y (вертикальная линия)
        val startY = centerY - graphHeight * cellSize // Начало оси Y с учетом масштаба
        val endY = centerY + graphHeight * cellSize // Конец оси Y с учетом масштаба
        canvas.drawLine(centerX, startY, centerX, endY, axisPaint)

        //todo вынести

        // Настройка кисти для текста
        val textPaint = Paint().apply {
            color = Color.BLACK
            textSize = 20f // Размер текста
            textAlign = Paint.Align.CENTER // Центрируем текст
        }

        // Рисуем метки и значения на оси X
        for (i in -graphWidth.toInt()..graphWidth.toInt() step 10) {
            val x = centerX + (i * cellSize) // Расстояние от центра оси X
            val y = centerY + 30 // Отступ от оси Y //todo +30 ?
            canvas.drawText(i.toString(), x, y, textPaint)
        }

        // Рисуем метки и значения на оси Y
        for (i in -graphHeight.toInt()..graphHeight.toInt() step 10) {
            val x = centerX + 30 // Отступ от оси X //todo +30 ?
            val y = centerY - (i * cellSize) // Для вертикальных меток меняем расчет по y
            canvas.drawText(i.toString(), x, y, textPaint)
        }

        val linePaint = Paint().apply {
            color = Color.LTGRAY // Цвет линии
            strokeWidth = 2f    // Толщина линии
            isAntiAlias = true
        }

        // Рисуем вертикальные линии
        for (i in -graphWidth.toInt()..graphWidth.toInt() step 10) {
            val x = centerX + (i * cellSize)
            // Рисуем линию с ограничением по оси Y
            canvas.drawLine(x, startY, x, endY, linePaint)
        }

        // Рисуем горизонтальные линии
        for (i in -graphHeight.toInt()..graphHeight.toInt() step 10) {
            val y = centerY - (i * cellSize)
            // Рисуем линию с ограничением по оси X
            canvas.drawLine(startX, y, endX, y, linePaint)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvasTest = canvas
        //todo 160f  magic number
        cellSize = minOf(width, height) / 160f  // Масштабируем размер ячеек
        println("---cellSize---${cellSize}")

        // Сохраняем состояние канвы перед трансформациями
        canvas.save()

        // Применяем масштаб и смещение
        canvas.scale(scaleFactor, scaleFactor, width / 2f, height / 2f)
        canvas.translate(offsetX / scaleFactor, offsetY / scaleFactor)

        // Ваши действия по рисованию
        drawAxesAndLabels(canvas)
        drawPointsAndLines(canvas)

        // Восстанавливаем состояние канвы
        canvas.restore()
    }

    private fun resetGraphToInitialState() {
        scaleFactor = 1f
        offsetX = 0f
        offsetY = 0f
        invalidate()
    }



    /** Zoom functionality */

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event) // Обрабатываем жесты масштабирования
        gestureDetector.onTouchEvent(event) // Передаем событие в GestureDetector
        return true
    }
        //todo куда вынести ScaleListener
    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {

            // Новый масштаб с учетом ограничения
            val newScaleFactor = (scaleFactor * detector.scaleFactor).coerceIn(0.5f, 3f)
            val scaleDelta = newScaleFactor / scaleFactor
            scaleFactor = newScaleFactor

            // Точка фокуса в текущей системе координат
            val focusX = detector.focusX
            val focusY = detector.focusY

            println("---focusX---$focusX---focusY---$focusY")

            // Коэффициент для уменьшения чувствительности смещения (например, 0.5f)
            val smoothingFactor = 0.5f



            // Пересчет смещения: зум должен происходить относительно точки фокуса
            offsetX += (focusX - width / 2f - offsetX) * (1 - scaleDelta) * smoothingFactor
            offsetY += (focusY - height / 2f - offsetY) * (1 - scaleDelta) * smoothingFactor

            invalidate() // Обновляем View


            return true
        }
    }

    /** gesture functionality */
    //todo куда вынести GestureListener
    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            resetGraphToInitialState() // Сбрасываем график на исходное состояние
            return true
        }
        override fun onDown(event: MotionEvent): Boolean = true

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            // Уменьшаем чувствительность смещения
            val sensitivity = 0.8f / scaleFactor.coerceAtLeast(0.1f)

            // Обновляем смещение
            offsetX -= distanceX * sensitivity
            offsetY -= distanceY * sensitivity

            // Вычисляем размеры графика
            val graphWidthInPixels = (graphWidth * cellSize * scaleFactor).coerceAtLeast(1f)
            val graphHeightInPixels = (graphHeight * cellSize * scaleFactor).coerceAtLeast(1f)

            println("---±±±graphWidthInPixels---|${graphWidthInPixels}|")
            println("---±±±graphHeightInPixels---|${graphHeightInPixels}|")


            // Размеры экрана
            val screenWidth = width.toFloat()
            val screenHeight = height.toFloat()

            println("---±±±screenWidth---|${screenWidth}|")
            println("---±±±screenHeight---|${screenHeight}|")

            // Определяем границы смещения для крайних линий
            var minX = -(graphWidthInPixels / 2) + (screenWidth / 2)
            var maxX = (graphWidthInPixels / 2) - (screenWidth / 2)

            // Проверяем корректность minX и maxX
            if (minX > maxX) {
                val temp = minX
                minX = maxX
                maxX = temp
            }

            var minY = -(graphHeightInPixels / 2) + (screenHeight / 2)
            var maxY = (graphHeightInPixels / 2) - (screenHeight / 2)

            // Проверяем корректность minY и maxY
            if (minY > maxY) {
                val temp = minY
                minY = maxY
                maxY = temp
            }

            // Корректируем offsetX и offsetY
            offsetX = offsetX.coerceIn(minX, maxX)
            offsetY = offsetY.coerceIn(minY, maxY)

            // Перерисовываем экран
            invalidate()
            return true
        }
    }
    //todo как лучше прокидывать данные в эту вью
    fun setData(newData: List<PointUI>) {
        points.clear()
        points.addAll(newData)
        invalidate()
    }

    companion object {
        //todo constants
    }

}