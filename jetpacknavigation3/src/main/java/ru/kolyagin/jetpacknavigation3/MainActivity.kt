package ru.kolyagin.jetpacknavigation3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import ru.kolyagin.jetpacknavigation3.samples.s13.Sample13
import ru.kolyagin.jetpacknavigation3.ui.theme.NavigationWithoutPainTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationWithoutPainTheme {
                Scaffold(modifier = Modifier.systemBarsPadding()) {
                    Box(modifier = Modifier.padding(it)) {
                        Sample13()
                    }
                }
            }
        }
    }
}