package com.ai.customui

import android.graphics.Paint
import android.graphics.Color as ColorOld
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ai.customui.ui.theme.CustomUITheme
import kotlin.math.pow
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
    scaleStyle: ScaleStyle = ScaleStyle()
) {
    val radius = scaleStyle.radius
    val scaleWidth = scaleStyle.scaleWidth

    // the center of the canvas
    var center by remember {
        mutableStateOf(Offset.Zero)
    }

    // The center of the scale
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    Canvas(modifier = modifier) {

        center = this.center

        circleCenter = Offset(
            center.x,
            scaleWidth.toPx() / 2f + radius.toPx()
        )

        // shadow around the scale widget
        drawContext.canvas.nativeCanvas.apply {
            drawCircle(
                circleCenter.x,
                circleCenter.y,
                radius.toPx(),
                Paint().apply {
                    strokeWidth = scaleWidth.toPx()
                    color = android.graphics.Color.WHITE
                    setStyle(Paint.Style.STROKE)
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

}

data class ScaleStyle(
    val scaleWidth: Dp = 100.dp,
    val radius: Dp = 550.dp,
    val normalLineColor: Color = Color.LightGray,
    val fiveStepLineColor: Color = Color.Green,
    val tenStepLineColor: Color = Color.Black
)