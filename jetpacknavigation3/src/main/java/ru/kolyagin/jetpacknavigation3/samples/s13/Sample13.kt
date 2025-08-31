package ru.kolyagin.jetpacknavigation3.samples.s13

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

@Serializable
data object List : NavKey

@Serializable
data class Details(val item: Item) : NavKey

@Serializable
data class Item(
    val stringField: String,
    val intField: Int,
    val nullableIntField: Int?
)

@Composable
fun Sample13() {
    val backStack = rememberNavBackStack(Home)

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.isNotEmpty()) {
                backStack.removeAt(backStack.lastIndex)
            }
        },
        entryProvider = { navKey ->
            when (navKey) {
                is Home -> NavEntry(navKey) {
                    HomeScreen(
                        onNavigateToList = { backStack.add(List) }
                    )
                }

                is List -> NavEntry(navKey) {
                    ListScreen(
                        onNavigateToDetails = { item ->
                            backStack.add(Details(item))
                        }
                    )
                }

                is Details -> NavEntry(navKey) {
                    DetailsScreen(item = navKey.item)
                }

                else -> NavEntry(navKey) {
                    SafeEmptyScreen()
                }
            }
        }
    )
}

@Composable
private fun HomeScreen(onNavigateToList: () -> Unit) {
    Button(onClick = onNavigateToList) {
        Text("Navigate to List")
    }
}

@Composable
private fun ListScreen(onNavigateToDetails: (Item) -> Unit) {
    val list = remember { generateList() }
    Column {
        list.forEach { item ->
            Button(onClick = { onNavigateToDetails(item) }) {
                Text("Navigate to ${item.stringField}")
            }
        }
    }
}

@Composable
private fun DetailsScreen(item: Item) { /* Some content */ }

@Composable
private fun SafeEmptyScreen() { /* Some content */ }

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