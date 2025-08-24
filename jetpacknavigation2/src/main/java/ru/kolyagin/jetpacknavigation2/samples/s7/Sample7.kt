package ru.kolyagin.jetpacknavigation2.samples.s7

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.kolyagin.jetpacknavigation2.samples.s7.DetailsDestination.Companion.detailsComposable
import ru.kolyagin.jetpacknavigation2.samples.s7.HomeDestination.Companion.homeComposable
import ru.kolyagin.jetpacknavigation2.samples.s7.ListDestination.Companion.listComposable

//Аргументы (java.lang.IllegalArgumentException: integer does not allow nullable values)

@Composable
fun Sample7() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HomeDestination.createRoute()) {
        homeComposable(navController)

        listComposable(navController)

        detailsComposable()
    }
}

private data class Item(
    val stringField: String,
    val intField: Int,
    val nullableIntField: Int?,
)

private abstract class Destination {

    abstract fun getNavigationRoute(): String

}

private fun NavHostController.navigateTo(destination: Destination) {
    navigate(destination.getNavigationRoute())
}

private class HomeDestination : Destination() {

    override fun getNavigationRoute(): String = route

    companion object {
        private const val route = "home"

        fun NavGraphBuilder.homeComposable(navController: NavController) {
            composable(this@Companion.route) { HomeScreen(navController) }
        }

        fun createRoute() = route
    }
}

private class ListDestination : Destination() {
    override fun getNavigationRoute(): String = route

    companion object {
        private const val route = "list"

        fun NavGraphBuilder.listComposable(navController: NavHostController) {
            composable(this@Companion.route) { ListScreen(navController) }
        }

        fun createRoute() = route
    }
}

private class DetailsDestination(private val item: Item) : Destination() {
    override fun getNavigationRoute(): String {
        return createRoute(item)
    }

    companion object {
        private const val route = "details/{stringValue}/{intValue}/?value={value}"

        fun NavGraphBuilder.detailsComposable() {
            composable(
                route = this@Companion.route,
                arguments = listOf(
                    navArgument("stringValue") { type = NavType.StringType },
                    navArgument("intValue") { type = NavType.IntType },
                    navArgument("value") {
                        type = NavType.IntType
                        nullable = true
                        defaultValue = null
                    },
                )
            ) { backStackEntry ->
                val stringValue = backStackEntry.arguments?.getString("stringValue")!!
                val intValue = backStackEntry.arguments?.getInt("intValue")!!
                val nullableIntField = backStackEntry.arguments?.getInt("value")
                DetailsScreen(
                    item = Item(
                        stringField = stringValue,
                        intField = intValue,
                        nullableIntField = nullableIntField
                    )
                )
            }
        }

        fun createRoute(item: Item): String {
            return "details/${item.stringField}/${item.intField}/?value=${item.nullableIntField}"
        }
    }
}

@Composable
private fun HomeScreen(navController: NavController) {
    Button(onClick = { navController.navigate(ListDestination()) }) {
        Text("Navigate to List")
    }
}

@Composable
private fun ListScreen(navController: NavHostController) {
    val list = remember { generateList() }
    Column {
        list.forEach { item ->
            Button(onClick = { navController.navigateTo(DetailsDestination(item)) }) {
                Text("Navigate to ${item.stringField}")
            }
        }
    }
}

private fun generateList() = listOf(
    Item(
        stringField = "string1",
        intField = 1,
        nullableIntField = null,
    ),
    Item(
        stringField = "string2",
        intField = 2,
        nullableIntField = 2,
    ),
    Item(
        stringField = "string3/string",
        intField = 3,
        nullableIntField = null,
    ),
)

@Composable
private fun DetailsScreen(item: Item) { /* Some content */ }