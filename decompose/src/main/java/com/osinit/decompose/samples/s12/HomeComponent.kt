package com.osinit.decompose.samples.s12

import com.arkivanov.decompose.ComponentContext

internal interface HomeComponent {

    fun onButtonClick()

    fun interface Factory {


        operator fun invoke(
            componentContext: ComponentContext,
            onGoToList: () -> Unit,
        ): HomeComponent
    }
}

internal class DefaultHomeComponent(
    componentContext: ComponentContext,
    private val onGoToList: () -> Unit,
): HomeComponent, BaseComponent(componentContext) {

    override fun onButtonClick() {
        onGoToList()
    }

    class Factory : HomeComponent.Factory {

        override fun invoke(
            componentContext: ComponentContext,
            onGoToList: () -> Unit,
        ): HomeComponent {
            return DefaultHomeComponent(
                componentContext = componentContext,
                onGoToList = onGoToList,
            )
        }
    }
}