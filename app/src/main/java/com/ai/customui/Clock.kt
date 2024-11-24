package com.ai.customui

import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.Px
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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

            drawClock(
                nativeCanvas = nativeCanvas,
                canvasCenter = canvasCenter,
                clockRadiusPx = clockStyle.radius.toPx(),
                clockStrokePx = 1.dp.toPx()
            )
        }
    )

}

private fun drawClock(
    nativeCanvas: Canvas,
    canvasCenter: Offset,
    clockRadiusPx: Float,
    clockStrokePx: Float
) {
    nativeCanvas.apply {
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
}

data class ClockStyle(
    val radius : Dp = 150.dp,
    val oneStepLineColor: Color = Color.Gray,
    val fiveStepLineColor : Color = Color.Black,
    val oneStepLineLength : Dp = 10.dp,
    val fiveStepLineLength : Dp = 15.dp
)