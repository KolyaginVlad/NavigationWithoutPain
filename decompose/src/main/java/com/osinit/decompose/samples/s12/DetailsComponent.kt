package com.osinit.decompose.samples.s12

import com.arkivanov.decompose.ComponentContext

internal interface DetailsComponent {

    fun interface Factory {

        operator fun invoke(
            componentContext: ComponentContext,
            item: Item,
        ): DetailsComponent
    }
}

internal class DefaultDetailsComponent(
    componentContext: ComponentContext,
    item: Item,
): DetailsComponent, BaseComponent(componentContext) {


    class Factory : DetailsComponent.Factory {

        override fun invoke(
            componentContext: ComponentContext,
            item: Item,
        ): DetailsComponent {
            return DefaultDetailsComponent(
                componentContext = componentContext,
                item = item,
            )
        }
    }
}