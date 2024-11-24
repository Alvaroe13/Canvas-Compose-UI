package com.ai.customui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.pow
import kotlin.math.sqrt


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