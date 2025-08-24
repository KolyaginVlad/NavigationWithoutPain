package ru.kolyagin.jetpacknavigation2.samples.s1

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Sample1() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen() }

        composable("list") { ListScreen() }
    }
}

@Composable
private fun HomeScreen() { /* Some content */}

@Composable
private fun ListScreen() { /* Some content */}