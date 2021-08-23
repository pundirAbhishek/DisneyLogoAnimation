package com.example.disneypluslogoanimation

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.disneypluslogoanimation.ui.theme.*

@Composable
fun DisneyLogoAnimation() {
    val sweepAngle = 135f
    val animationDuration = 1000
    val plusAnimationDuration = 300
    val animationDelay = 0
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    var plusAnimationPlayed by remember {
        mutableStateOf(false)
    }

    val currentPercent = animateFloatAsState(
        targetValue = if (animationPlayed) sweepAngle else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay,
            easing = FastOutLinearInEasing
        ),
        finishedListener = {
            plusAnimationPlayed = true
        }
    )

    val scalePercent = animateFloatAsState(
        targetValue = if (plusAnimationPlayed) 1f else 0f,
        animationSpec = tween(
            durationMillis = plusAnimationDuration,
            delayMillis = animationDelay
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier
            .size(200.dp)
            .drawBehind {
                drawArc(
                    brush = Brush.linearGradient(
                        0f to GradientColor1,
                        0.2f to GradientColor2,
                        0.35f to GradientColor3,
                        0.45f to GradientColor4,
                        0.75f to GradientColor5,
                    ),
                    startAngle = -152f,
                    sweepAngle = currentPercent.value,
                    useCenter = false,
                    style = Stroke(width = 10f, cap = StrokeCap.Round)
                )
            }) { }
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_disney_logo_text),
                contentDescription = "Disney Logo Text",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(200.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = "Plus Image",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterVertically)
                    .scale(scalePercent.value)
            )
        }
    }
}

