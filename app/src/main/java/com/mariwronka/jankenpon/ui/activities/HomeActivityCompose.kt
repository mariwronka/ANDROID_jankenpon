package com.mariwronka.jankenpon.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mariwronka.jankenpon.R
import com.mariwronka.jankenpon.ui.theme.JankenponTheme

class HomeActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(LocalContext.current)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeScreenPreview() {
    JankenponTheme {
        HomeScreen(LocalContext.current)
    }
}

@Composable
fun HomeScreen(current: Context) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
    ) {
        BackgroundImage()
        ContentColumn(current)
    }
}

@Composable
private fun BackgroundImage() {
    Image(
        painter = painterResource(id = R.drawable.bg_default),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds,
    )
}

@Composable
private fun ContentColumn(current: Context) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        AnimatedLogo()

        Button(
            onClick = {
                current.startActivity(Intent(current, GameActivity::class.java))
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow),
        ) {
            Text(
                text = "Vamos começar",
                color = Color.Black,
                fontSize = 16.sp,
                letterSpacing = 0.8.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                current.startActivity(Intent(current, RankingActivity::class.java))
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        ) {
            Text(
                text = "Ranking",
                color = Color.Black,
                fontSize = 16.sp,
                letterSpacing = 0.8.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
            )
        }
    }
}

@Composable
fun AnimatedLogo(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "logo wiggle")

    val offsetX by infiniteTransition.animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 400),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "offsetX",
    )

    Image(
        painter = painterResource(id = R.drawable.logo_jankenpon),
        contentDescription = "Logo Jankenpon",
        modifier = modifier
            .offset(x = offsetX.dp)
            .padding(bottom = 32.dp)
            .height(300.dp)
            .width(300.dp),
        contentScale = ContentScale.Fit,
    )
}
