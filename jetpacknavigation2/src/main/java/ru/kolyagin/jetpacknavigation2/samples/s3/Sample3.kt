package ru.kolyagin.jetpacknavigation2.samples.s3

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Sample3() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HomeDestination.route) {
        composable(HomeDestination.route) { HomeScreen() }

        composable(ListDestination.route) { ListScreen() }
    }
}

private class HomeDestination {

    companion object {
        const val route = "home"
    }
}

private class ListDestination {

    companion object {
        const val route = "list"
    }
}

@Composable
private fun HomeScreen() { /* Some content */}

@Composable
private fun ListScreen() { /* Some content */}