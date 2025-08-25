package ru.kolyagin.jetpacknavigation2.samples.s10


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import ru.kolyagin.jetpacknavigation2.samples.s10.DetailsDestination.Companion.detailsComposable
import ru.kolyagin.jetpacknavigation2.samples.s10.HomeDestination.Companion.homeComposable
import ru.kolyagin.jetpacknavigation2.samples.s10.ListDestination.Companion.listComposable
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

//Аргументы, json

@Composable
fun Sample10() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HomeDestination.createRoute()) {
        homeComposable(navController)

        listComposable(navController)

        detailsComposable()
    }
}

@Serializable
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

        fun NavGraphBuilder.homeComposable(navController: NavHostController) {
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
        private const val route = "details/?value={value}"

        @OptIn(ExperimentalSerializationApi::class)
        fun NavGraphBuilder.detailsComposable() {
            composable(
                route = this@Companion.route,
                arguments = listOf(
                    navArgument("value") { type = NavType.StringType },
                )
            ) { backStackEntry ->
                val value = Json.decodeFromString<Item>(
                    URLDecoder.decode(
                        backStackEntry.arguments?.getString("value")!!,
                        StandardCharsets.UTF_8.toString()
                    )
                )
                DetailsScreen(
                    item = value
                )
            }
        }

        fun createRoute(item: Item): String {
            return "details/?value=${
                URLEncoder.encode(Json.encodeToString(item), StandardCharsets.UTF_8.toString())
            }"
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