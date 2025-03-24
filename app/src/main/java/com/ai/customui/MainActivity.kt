package com.ai.customui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ai.customui.ui.theme.CustomUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomUITheme {

                Box(modifier = Modifier.fillMaxSize()) {

                    //MyCanvasSquare()

                    Scale(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                            .align(Alignment.BottomCenter),
                        scaleStyle = ScaleStyle(
                            scaleWidth = 150.dp
                        ),
                        initialWeight = 68
                    )

//                    Clock(
//                        modifier = Modifier
//                            .fillMaxSize()
//                    )


                }

            }
        }
    }
}