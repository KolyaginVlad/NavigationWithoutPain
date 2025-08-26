package com.osinit.composedestinations.samples.s11

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.DetailsScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ListScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.serialization.Serializable

//Compose Destinations

@Composable
fun Sample11() {
    DestinationsNavHost(navGraph = NavGraphs.root)
}

@Serializable
data class Item(
    val stringField: String,
    val intField: Int,
    val nullableIntField: Int?,
)

@Destination<RootGraph>(start = true)
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {
    Button(onClick = { navigator.navigate(ListScreenDestination()) }) {
        Text("Navigate to List")
    }
}

@Destination<RootGraph>
@Composable
fun ListScreen(navigator: DestinationsNavigator) {
    val list = remember { generateList() }
    Column {
        list.forEach { item ->
            Button(onClick = {  navigator.navigate(DetailsScreenDestination(item)) }) {
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

@Destination<RootGraph>
@Composable
fun DetailsScreen(item: Item) { /* Some content */ }