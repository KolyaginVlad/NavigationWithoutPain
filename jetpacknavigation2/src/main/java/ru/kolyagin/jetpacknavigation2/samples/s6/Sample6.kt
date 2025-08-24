package ru.kolyagin.jetpacknavigation2.samples.s6

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.kolyagin.jetpacknavigation2.samples.s6.HomeDestination.Companion.homeComposable
import ru.kolyagin.jetpacknavigation2.samples.s6.ListDestination.Companion.listComposable

//Вынесение composable в Destination

@Composable
fun Sample6() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HomeDestination.route) {
        homeComposable(navController)

        listComposable()
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

        fun NavGraphBuilder.homeComposable(navController: NavHostController) {
            composable(this@Companion.route) { HomeScreen(navController) }
        }
    }
}

private class ListDestination : Destination() {
    override fun getNavigationRoute(): String = route

    companion object {
        const val route = "list"

        fun NavGraphBuilder.listComposable() {
            composable(this@Companion.route) { ListScreen() }
        }
    }
}

@Composable
private fun HomeScreen(navController: NavHostController) {
    Button(onClick = { navController.navigateTo(ListDestination()) }) {
        Text("Navigate to List")
    }
}

@Composable
private fun ListScreen() { /* Some content */ }