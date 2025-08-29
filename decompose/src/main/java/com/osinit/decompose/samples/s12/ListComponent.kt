package com.osinit.decompose.samples.s12

import com.arkivanov.decompose.ComponentContext

internal interface ListComponent {

    fun onItemClick(item: Item)

    fun interface Factory {


        operator fun invoke(
            componentContext: ComponentContext,
            onGoToDetails: (Item) -> Unit,
        ): ListComponent
    }
}

internal class DefaultListComponent(
    componentContext: ComponentContext,
    private val onGoToDetails: (Item) -> Unit,
): ListComponent, BaseComponent(componentContext) {

    override fun onItemClick(item: Item) {
        onGoToDetails(item)
    }

    class Factory : ListComponent.Factory {

        override fun invoke(
            componentContext: ComponentContext,
            onGoToDetails: (Item) -> Unit,
        ): ListComponent {
            return DefaultListComponent(
                componentContext = componentContext,
                onGoToDetails = onGoToDetails,
            )
        }
    }
}