package com.ai.customui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ai.customui.ui.theme.CustomUITheme

@Composable
fun PathBasicsSquare() {
    Canvas(modifier = Modifier.fillMaxSize()) {

        // square
        val path = Path().apply {
            moveTo(100f,100f)
            lineTo(100f, 500f)
            lineTo(500f , 500f)
            lineTo(500f, 100f)
            //lineTo(100f, 100f) same as 'close()'
            close()
        }

        drawPath(
            path = path,
            color = Color.Green,
            style = Stroke(width = 5.dp.toPx())
        )
    }
}

@Composable
fun PathBasicsSquareRoundedQuadraticBezier() {
    Canvas(modifier = Modifier.fillMaxSize()) {

        //add curve to the square declared above
        // square
        val path = Path().apply {
            moveTo(100f, 100f)
            lineTo(100f, 500f)
            lineTo(500f, 500f)
            //lineTo(500f, 100f)
            //lineTo(100f, 100f) same as 'close()'
            quadraticBezierTo(800f,300f,500f,100f)
            close()
        }

        drawPath(
            path = path,
            color = Color.Green,
            style = Stroke(width = 5.dp.toPx())
        )
    }
}

@Composable
fun PathBasicsSquareRoundedCubicBezier() {
    Canvas(modifier = Modifier.fillMaxSize()) {

        //add curve to the square declared above
        // square
        val path = Path().apply {
            moveTo(100f, 100f)
            lineTo(100f, 500f)
            lineTo(500f, 500f)
            //lineTo(500f, 100f)
            //lineTo(100f, 100f) same as 'close()'
            cubicTo(800f, 500f, 800f, 100f, 500f, 100f)
            close()
        }

        drawPath(
            path = path,
            color = Color.Green,
            style = Stroke(width = 5.dp.toPx())
        )
    }
}

@Composable
fun PathBasicsSquareRoundedCubicBezierStyle() {
    Canvas(modifier = Modifier.fillMaxSize()) {

        //add curve to the square declared above
        // square
        val path = Path().apply {
            moveTo(100f, 100f)
            lineTo(100f, 500f)
            lineTo(500f, 500f)
            //lineTo(500f, 100f)
            //lineTo(100f, 100f) same as 'close()'
            cubicTo(800f, 500f, 800f, 100f, 500f, 100f)
            //close()
        }

        drawPath(
            path = path,
            color = Color.Green,
            style = Stroke(
                width = 10.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round // rounds the corner between 2 lines
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun SquarePreview() {
    CustomUITheme {
        Box(modifier = Modifier.fillMaxSize()) {
            PathBasicsSquare()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SquareRoundedQuadraticBezierPreview() {
    CustomUITheme {
        Box(modifier = Modifier.fillMaxSize()) {
            PathBasicsSquareRoundedQuadraticBezier()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SquareRoundedQCubicBezierPreview() {
    CustomUITheme {
        Box(modifier = Modifier.fillMaxSize()) {
            PathBasicsSquareRoundedCubicBezier()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SquareRoundedQCubicBezierStylePreview() {
    CustomUITheme {
        Box(modifier = Modifier.fillMaxSize()) {
            PathBasicsSquareRoundedCubicBezierStyle()
        }
    }
}