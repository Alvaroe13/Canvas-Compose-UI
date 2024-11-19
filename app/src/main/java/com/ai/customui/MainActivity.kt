package com.ai.customui

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Align
import android.graphics.Color as ColorOld
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.Px
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ai.customui.ui.theme.CustomUITheme
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomUITheme {

                Box(modifier = Modifier.fillMaxSize()) {

                    Scale(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                            .align(Alignment.BottomCenter),
                        scaleStyle = ScaleStyle(
                            scaleWidth = 150.dp
                        )
                    )

                }

            }
        }
    }
}

@Composable
fun MyCanvasSquare() {
    Canvas(
        modifier = Modifier
            .padding(20.dp)
            .size(300.dp)
            .pointerInput(true) {

                //detect click event in canvas
                detectTapGestures {
                    val x = it.x
                    val y = it.y
                    val distance = sqrt(x.pow(2).toDouble())
                    //x/y coordinates in screen where the tap event happened
                }
            },
        onDraw = {
            //black big box
            drawRect(
                color = Color.Black,
                size = size
            )

            // red inner square
            val portionWidth = (size.width * 0.05).toFloat()
            val portionHeight = (size.height * 0.25).toFloat()
            drawRect(
                color = Color.Red,
                topLeft = Offset(portionWidth , portionHeight),
                size = Size(size.width / 2 , size.height / 2),
                style = Stroke(
                    width = 5.dp.toPx()
                )
            )

            // inner gradient circle
            val circleOffsetWidth = (size.width * 0.8).toFloat()
            val circleOffsetHeight = (size.width * 0.85).toFloat()
            drawCircle(
                center = Offset(circleOffsetWidth, circleOffsetHeight),
                radius = 100f,
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.Red , Color.Blue
                    ),
                    center = center,
                    radius = 100f
                )
            )
        }
    )
}

@Composable
fun Scale(
    modifier: Modifier = Modifier,
    scaleStyle: ScaleStyle = ScaleStyle(),
    minWeight: Int = 20,
    maxWeight: Int = 200,
    initialWeight: Int = 70
) {
    val radius = scaleStyle.radius
    val scaleWidth = scaleStyle.scaleWidth

    // the center of the canvas
    var center by remember { mutableStateOf(Offset.Zero) }

    // The center of the scale
    var circleCenter by remember { mutableStateOf(Offset.Zero) }

    val angle by remember { mutableStateOf(0f) }

    Canvas(modifier = modifier) {

        center = this.center

        circleCenter = Offset(x = center.x, y = scaleWidth.toPx() / 2 + radius.toPx())

        //radius from circle center to outer and inner edge of the scale
        val outerRadius = radius.toPx() + scaleWidth.toPx() / 2f
        val innerRadius = radius.toPx() - scaleWidth.toPx() / 2f

        val nativeCanvas = drawContext.canvas.nativeCanvas

        // Scale and shadow around it
        drawScale(
            nativeCanvas = nativeCanvas,
            circleCenter = circleCenter,
            radiusInPx = radius.toPx(),
            scaleWidthInPx = scaleWidth.toPx()
        )

        // lines for weight
        drawScaleWeightLines(
            minWeight = minWeight,
            maxWeight = maxWeight,
            initialWeight = initialWeight,
            angle = angle,
            scaleStyle = scaleStyle,
            outerRadius = outerRadius,
            circleCenter = circleCenter,
            drawScope = this,
            nativeCanvas = nativeCanvas
        )

    }

}

private fun drawScale(
    nativeCanvas: Canvas,
    circleCenter: Offset,
    radiusInPx: Float,
    scaleWidthInPx: Float
) {
    nativeCanvas.apply {
        drawCircle(
            circleCenter.x,
            circleCenter.y,
            radiusInPx,
            Paint().apply {
                strokeWidth = scaleWidthInPx
                color = android.graphics.Color.WHITE
                style = Paint.Style.STROKE
                setShadowLayer(
                    60f,
                    0f,
                    0f,
                    ColorOld.argb(50,0,0,0)
                )
            }
        )
    }
}

private fun drawScaleWeightLines(
    minWeight: Int,
    maxWeight: Int,
    initialWeight: Int,
    angle : Float,
    scaleStyle: ScaleStyle,
    outerRadius : Float,
    circleCenter: Offset,
    drawScope: DrawScope,
    nativeCanvas: Canvas
) {

    drawScope.apply {
        for(i in minWeight..maxWeight) {

            // TODO "angle" what for?
            val angleInDegrees = i - initialWeight + angle - 90

            // angle needed in radians for "sin" & "cos" kotlin functions
            // this angle represents how inclined the line is to the center of the scale (lines representing the weight)
            val angleInRadians = angleInDegrees * (PI / 180).toFloat() // angle multiplied by "(PI / 180)" converts the value from degrees to radians

            val lineType = when {
                i % 10 == 0 -> LineType.TenStepLine
                i % 5 == 0 -> LineType.FiveStepLine
                else -> LineType.SingleStepLine
            }

            val lineLength = when(lineType) {
                is LineType.TenStepLine -> scaleStyle.tenStepLineLength.toPx()
                is LineType.FiveStepLine -> scaleStyle.fiveStepLineLength.toPx()
                is LineType.SingleStepLine -> scaleStyle.oneStepLineLength.toPx()
            }

            val lineColor = when(lineType) {
                is LineType.TenStepLine -> scaleStyle.tenStepLineColor
                is LineType.FiveStepLine -> scaleStyle.fiveStepLineColor
                is LineType.SingleStepLine -> scaleStyle.oneStepLineColor
            }

            //(x,y) coordinates for the start of the lines
            val lineStart = Offset(
                x = (outerRadius - lineLength) * cos(angleInRadians) + circleCenter.x,
                y = (outerRadius - lineLength) * sin(angleInRadians) + circleCenter.y
            )

            //(x,y) coordinates for the end of the lines, touching the outer edge of the scale
            val lineEnd = Offset(
                x = outerRadius * cos(angleInRadians) + circleCenter.x, // takes (0,0) according to video
                y = outerRadius * sin(angleInRadians) + circleCenter.y
            )

            drawLine(
                color = lineColor,
                start = lineStart,
                end = lineEnd,
                strokeWidth = 1.dp.toPx()
            )

            //-------------------------------- weight line numbers -------------------------------//

            if(lineType is LineType.TenStepLine) {
                val marginIndicatorToNumber = 5.dp.toPx()
                val textRadius = outerRadius - lineLength - marginIndicatorToNumber - scaleStyle.textSize.toPx()
                val numberCoordinates = Offset(
                    x = textRadius * cos(angleInRadians) + circleCenter.x,
                    y = textRadius * sin(angleInRadians) + circleCenter.y
                )

                nativeCanvas.apply {
                    drawText(
                        abs(i).toString(),
                        numberCoordinates.x,
                        numberCoordinates.y,
                        Paint().apply {
                            textSize = scaleStyle.textSize.toPx()
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }

            }

        }
    }


}

data class ScaleStyle(
    val scaleWidth: Dp = 100.dp,
    val radius: Dp = 550.dp,
    val oneStepLineColor: Color = Color.LightGray,
    val fiveStepLineColor: Color = Color.Green,
    val tenStepLineColor: Color = Color.Black,
    val oneStepLineLength: Dp = 15.dp,
    val fiveStepLineLength: Dp = 30.dp,
    val tenStepLineLength: Dp = 45.dp,
    val scaleIndicatorColor: Color = Color.Green,
    val scaleIndicatorLength : Dp = 60.dp,
    val textSize: TextUnit = 18.sp
)

sealed class LineType {
    object SingleStepLine : LineType()
    object FiveStepLine : LineType()
    object TenStepLine : LineType()
}