package ru.kolyagin.jetpacknavigation2.samples.s2

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Serializable
data object Home

@Serializable
data object List

@Composable
fun Sample2() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home) {
        composable(Home::class) { HomeScreen() }

        composable(List::class) { ListScreen() }
    }
}

@Composable
private fun HomeScreen() { /* Some content */}

@Composable
private fun ListScreen() { /* Some content */}