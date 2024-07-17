package com.kkdev.notable.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.kkdev.notable.MainActivity
import com.kkdev.notable.ui.theme.AppTheme
import com.kkdev.notable.ui.theme.urbanistFontFamily
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = AppTheme.colorScheme.background
                ) {
                    SplashScreen()
                }
            }
        }
    }

    @Composable
    private fun SplashScreen() {
        LaunchedEffect(Unit) {
            delay(2000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "notable",
                    style = MaterialTheme.typography.displaySmall,
                    fontFamily = urbanistFontFamily,
                    fontWeight = FontWeight.SemiBold,                    color = AppTheme.colorScheme.onBackground
                )
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun PreviewSplashScreen() {
        AppTheme {
            SplashScreen()
        }
    }
}
