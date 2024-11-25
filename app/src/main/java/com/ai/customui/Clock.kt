package com.ai.customui

import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.withRotation
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Clock(
    modifier: Modifier = Modifier,
    clockStyle: ClockStyle = ClockStyle()
) {

    Canvas(
        modifier = modifier,
        onDraw = {
            val canvasCenter = this.center
            val nativeCanvas = drawContext.canvas.nativeCanvas

            nativeCanvas.drawClock(
                canvasCenter = canvasCenter,
                clockRadiusPx = clockStyle.radius.toPx(),
                clockStrokePx = 1.dp.toPx()
            )

            drawClockLines(
                canvasCenter = canvasCenter,
                clockRadiusInPx = clockStyle.radius.toPx(),
                clockStyle = clockStyle,
                nativeCanvas = nativeCanvas
            )
        }
    )

}

private fun Canvas.drawClock(
    canvasCenter: Offset,
    clockRadiusPx: Float,
    clockStrokePx: Float
) {
    drawCircle(
        canvasCenter.x,
        canvasCenter.y,
        clockRadiusPx,
        Paint().apply {
            strokeWidth = clockStrokePx
            color = android.graphics.Color.LTGRAY
            style = Paint.Style.STROKE
            setShadowLayer(
                30f,
                0f,
                0f,
                android.graphics.Color.argb(255,0,0,0)
            )
        }
    )
}

private fun DrawScope.drawClockLines(
    clockStyle: ClockStyle,
    canvasCenter: Offset,
    clockRadiusInPx: Float,
    nativeCanvas: Canvas
) {

    val minutesRange : IntRange = 1..60
    for (i in minutesRange) {

        val lineType = when {
            i % 5 == 0 -> ClockLineType.FiveStepLine
            else -> ClockLineType.SingleStepLine
        }

        val lineLength = when(lineType) {
            is ClockLineType.FiveStepLine -> clockStyle.fiveStepLineLength.toPx()
            is ClockLineType.SingleStepLine -> clockStyle.oneStepLineLength.toPx()
        }

        val lineColor = when(lineType) {
            is ClockLineType.FiveStepLine -> clockStyle.fiveStepLineColor
            is ClockLineType.SingleStepLine -> clockStyle.oneStepLineColor
        }

        // we get the angle for each line by diving 360 degrees by number of lines to be shown in the clock (60)
        val angleInDegrees = i * (FULL_CIRCLE_ANGLE_DEGREES/ minutesRange.last) - 90
        val angleInRads = angleInDegrees.degreesToRadians()

        val lineStartOffset = Offset(
            x = (clockRadiusInPx - lineLength) * cos(angleInRads) + canvasCenter.x,
            y = (clockRadiusInPx - lineLength) * sin(angleInRads) + canvasCenter.y
        )

        val lineEndOffset = Offset(
            x = clockRadiusInPx * cos(angleInRads) + canvasCenter.x,
            y = clockRadiusInPx * sin(angleInRads) + canvasCenter.y
        )

        drawLine(
            color = lineColor,
            start = lineStartOffset,
            end = lineEndOffset,
            strokeWidth = 1.dp.toPx()
        )

        // ----------- numbers -----------//
        if(lineType is ClockLineType.FiveStepLine) {
            val text = if (i == minutesRange.last) 0 else i
            val marginIndicatorToNumber = 5.dp.toPx()
            val textRadius = clockRadiusInPx - lineLength - marginIndicatorToNumber - clockStyle.numberSize.toPx()
            val numberCoordinates = Offset(
                x = textRadius * cos(angleInRads) + canvasCenter.x,
                y = textRadius * sin(angleInRads) + canvasCenter.y
            )

            nativeCanvas.apply {
                drawText(
                    text.toString(),
                    numberCoordinates.x,
                    numberCoordinates.y,
                    Paint().apply {
                        textSize = clockStyle.numberSize.toPx()
                        textAlign = Paint.Align.CENTER
                    }
                )
            }

        }
    }
}

private const val  FULL_CIRCLE_ANGLE_DEGREES = 360f

// angle multiplied by "(PI / 180)" converts the value from degrees to radians
private fun Float.degreesToRadians () = this * (PI / 180).toFloat()

private fun Float.radiandsToDegrees () = this * (180 / PI ).toFloat()

data class ClockStyle(
    val radius : Dp = 150.dp,
    val oneStepLineColor: Color = Color.Gray,
    val fiveStepLineColor : Color = Color.Black,
    val oneStepLineLength : Dp = 15.dp,
    val fiveStepLineLength : Dp = 35.dp,
    val numberSize: Dp = 15.dp
)

sealed class ClockLineType {
    object SingleStepLine : ClockLineType()
    object FiveStepLine : ClockLineType()
}