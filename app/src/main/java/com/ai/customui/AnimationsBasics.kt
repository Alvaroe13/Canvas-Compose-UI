package com.ai.customui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.atan2
import android.graphics.Path as AndroidPath
import android.graphics.PathMeasure as AndroidPathMeasure

@Composable
fun AnimationsBasics() {

    val pathAnimatable = remember {
        Animatable(initialValue = 0f) // animation starting point (from the very top)
        // Animatable(initialValue = 0.7f) animation starting point (from 70% from the top), uncomment and see the difference
    }

    LaunchedEffect(key1 = true) {
        pathAnimatable.animateTo(
            targetValue = 1f, // animation finish point
            animationSpec = tween(durationMillis = 2500)
        )
    }

    val path = Path().apply {
        moveTo(100f, 100f) // starting location on screen
        // 'x1' & 'y1' coordinates for control causing the curve
        // 'x2' & 'y2' coordinates for path finish point
        quadraticBezierTo(400f, 200f, 100f, 400f)
    }

    val outputPath = Path()
    PathMeasure().apply {
        setPath(path, false)
        getSegment(0f, pathAnimatable.value * length, outputPath, true)
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawPath(
            path = outputPath,
            color = Color.Red,
            style = Stroke(width = 5.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Composable
fun AnimationWithArrow() {
    val pathAnimatable = remember {
        Animatable(initialValue = 0f) // animation starting point (from the very top)
    }

    LaunchedEffect(key1 = true) {
        pathAnimatable.animateTo(
            targetValue = 1f, // animation finish point
            animationSpec = tween(durationMillis = 2500)
        )
    }

    val path = Path().apply {
        moveTo(100f, 100f) // starting location on screen
        // 'x1' & 'y1' coordinates for control causing the curve
        // 'x2' & 'y2' coordinates for path finish point
        quadraticBezierTo(400f, 200f, 100f, 400f)
    }

    val outputPath = AndroidPath() //'AndroidPath' due to rename on import above (not real name)
    val position = FloatArray(2)
    val tangent = FloatArray(2)

    //'AndroidPathMeasure' due to rename on import above (not real name)
    AndroidPathMeasure().apply {
        setPath(path.asAndroidPath(), false)
        getSegment(0f, pathAnimatable.value * length, outputPath, true)
        getPosTan(pathAnimatable.value * length, position, tangent)
    }


    Canvas(modifier = Modifier.fillMaxSize()) {

        drawPath(
            path = outputPath.asComposePath(),
            color = Color.Red,
            style = Stroke(
                width = 5.dp.toPx(),
                cap = StrokeCap.Round
            )
        )

        //coordinates
        val x  = position[0]
        val y  = position[1]

        val degrees = -atan2(tangent[0] , tangent[1]) * (180f / PI.toFloat()) - 180f

        rotate( degrees = degrees , pivot = Offset(x, y)) {
            drawPath(
                path = Path().apply{
                    moveTo(x, y -30f)
                    lineTo(x - 30f , y + 60f)
                    lineTo(x + 30f , y + 60f)
                    close()
                },
                color = Color.Blue
            )
        }

    }


}