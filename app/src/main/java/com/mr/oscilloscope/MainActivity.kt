package com.mr.oscilloscope


import android.content.res.Resources
import android.os.Bundle
import kotlin.math.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mr.oscilloscope.ui.theme.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    var onMenu by remember {
        mutableStateOf(false)
    }
    if (onMenu) {
        Box(
            modifier = Modifier
                .zIndex(1f)
                .size(Dp(SCREEN_WIDTH + 1), Dp(SCREEN_HEIGHT) / 2)
                .background(Gray)
                .shadow(elevation = 2.dp, spotColor = Color.Black, ambientColor = Color.Black),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(modifier = Modifier
                .clickable { onMenu = false }
                .background(LightGray)
                .width(Dp(SCREEN_WIDTH + 1))
                .shadow(elevation = 2.dp, spotColor = Color.Black, ambientColor = Color.Black),
                contentAlignment = Alignment.BottomCenter,
            ) {
                Icon(
                    Icons.Default.KeyboardArrowUp,
                    contentDescription = "Меню",
                    modifier = Modifier.size(60.dp),
                    tint = Gray
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .clickable {
                    onMenu = true
                }
                .size(Dp(SCREEN_WIDTH + 1), Dp(166))
                .background(Gray)
                .shadow(elevation = 2.dp, spotColor = Color.Black, ambientColor = Color.Black),
            contentAlignment = Alignment.Center

        ) {
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = "Меню",
                modifier = Modifier.size(100.dp),
                tint = LightGray
            )
        }
    }
    Graphic()
}

fun fillPoints(): MutableList<Offset> {
    var points = mutableListOf<Offset>()
    for (x in 0..SCREEN_WIDTH.toInt()) {
        val y = (sin(x * (2f * PI /   SCREEN_WIDTH))
                * (SCREEN_HEIGHT / 2) + (SCREEN_HEIGHT / 2)).toFloat()
        points.add(Offset(x.toFloat(), y))
    }
    return points
}



@Composable
fun Graphic() {
    Canvas(
        modifier = Modifier
            .padding(top = Dp(166))
            .fillMaxSize()
            .background(color = DarkGreen)
            .clip(RectangleShape)
    ) {

        drawPoints(
            points = fillPoints(),
            strokeWidth = 3f,
            pointMode = PointMode.Lines,
            color = Color.White
        )


        drawLine(
            start = Offset(x = SCREEN_WIDTH.toFloat() / 2, y = SCREEN_HEIGHT.toFloat()),
            end = Offset(x = SCREEN_WIDTH.toFloat() / 2, y = 0f),
            color = White,
            strokeWidth = 4f
        )
        for (i in 0..SCREEN_WIDTH step 135) {
            drawLine(
                start = Offset(x = i.toFloat(), y = 0f),
                end = Offset(x = i.toFloat(), y = SCREEN_HEIGHT.toFloat()),
                color = LightGreen,
                strokeWidth = 1f
            )
        }
        for (i in 0..SCREEN_HEIGHT step 166) {
            drawLine(
                start = Offset(x = 0f, y = i.toFloat()),
                end = Offset(x = SCREEN_WIDTH.toFloat(), y = i.toFloat()),
                color = LightGreen,
                strokeWidth = 1f
            )
        }


    }
}


fun Dp(pxValue: Int): Dp {
    return (pxValue / Resources.getSystem().displayMetrics.density).toInt().dp
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen()
}
