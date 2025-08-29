package com.osinit.decompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.retainedComponent
import com.osinit.decompose.samples.s12.DefaultDetailsComponent
import com.osinit.decompose.samples.s12.DefaultHomeComponent
import com.osinit.decompose.samples.s12.DefaultListComponent
import com.osinit.decompose.samples.s12.DefaultRootComponent
import com.osinit.decompose.samples.s12.Sample12
import com.osinit.decompose.ui.theme.NavigationWithoutPainTheme

class MainActivity : ComponentActivity() {

    private val rootComponentFactory = DefaultRootComponent.Factory(
        homeComponent = DefaultHomeComponent.Factory(),
        listComponent = DefaultListComponent.Factory(),
        detailsComponent = DefaultDetailsComponent.Factory(),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val root = retainedComponent { componentContext ->
            rootComponentFactory(
                componentContext = componentContext,
            )
        }
        setContent {
            NavigationWithoutPainTheme {
                Scaffold(modifier = Modifier.systemBarsPadding()) {
                    Box(modifier = Modifier.padding(it)) {
                        Sample12(root)
                    }
                }
            }
        }
    }
}