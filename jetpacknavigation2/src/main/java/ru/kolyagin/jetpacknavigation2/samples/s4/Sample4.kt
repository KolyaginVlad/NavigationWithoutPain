package ru.kolyagin.jetpacknavigation2.samples.s4

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

//Навигация

@Composable
fun Sample4() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HomeDestination.route) {
        composable(HomeDestination.route) { HomeScreen(navController) }

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
private fun HomeScreen(navController: NavHostController) {
    Button(onClick = { navController.navigate(ListDestination.route) }) {
        Text("Navigate to List")
    }
}

@Composable
private fun ListScreen() { /* Some content */}