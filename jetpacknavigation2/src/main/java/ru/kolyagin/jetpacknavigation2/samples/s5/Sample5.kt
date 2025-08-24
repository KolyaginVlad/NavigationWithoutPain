package ru.kolyagin.jetpacknavigation2.samples.s5

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

//Extension navigate и базовый класс

@Composable
fun Sample5() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HomeDestination.route) {
        composable(HomeDestination.route) { HomeScreen(navController) }

        composable(ListDestination.route) { ListScreen() }
    }
}

private abstract class Destination {

    abstract fun getNavigationRoute(): String

}

private fun NavHostController.navigateTo(destination: Destination) {
    navigate(destination.getNavigationRoute())
}

private class HomeDestination : Destination() {

    override fun getNavigationRoute(): String = route

    companion object {
        const val route = "home"
    }
}

private class ListDestination : Destination() {
    override fun getNavigationRoute(): String = route

    companion object {
        const val route = "list"
    }
}

@Composable
private fun HomeScreen(navController: NavHostController) {
    Button(onClick = { navController.navigateTo(destination = ListDestination()) }) {
        Text("Navigate to List")
    }
}

@Composable
private fun ListScreen() { /* Some content */ }