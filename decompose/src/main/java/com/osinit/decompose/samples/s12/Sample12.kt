package com.osinit.decompose.samples.s12

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children

@Composable
internal fun Sample12(rootComponent: RootComponent) {
    Children(
        stack = rootComponent.stack,
        modifier = Modifier.fillMaxSize(),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.HomeChild -> HomeScreen(child.component)
            is RootComponent.Child.ListChild -> ListScreen(child.component)
            is RootComponent.Child.DetailsChild -> DetailsScreen(child.component)
        }
    }
}

@Composable
private fun HomeScreen(component: HomeComponent) {
    Button(onClick = component::onButtonClick) {
        Text("Navigate to List")
    }
}

@Composable
private fun ListScreen(component: ListComponent) {
    val list = remember { generateList() }
    Column {
        list.forEach { item ->
            Button(onClick = { component.onItemClick(item) } ) {
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
private fun DetailsScreen(component: DetailsComponent) { /* Some content */ }