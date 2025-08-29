package com.osinit.decompose.samples.s12

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

/**
 * Компонент, управляющий экранами подключения устройства и контентом приложения
 */
internal interface RootComponent {
    /**
     * Стек экранов
     */
    val stack: Value<ChildStack<*, Child>>

    /**
     * Функция для возвращения по backstack по индексу
     */
    fun onBackClicked(toIndex: Int)


    /**
     * Определение всех возможных дочерних компонентов
     */
    sealed class Child {
        class HomeChild(val component: HomeComponent) : Child()
        class ListChild(val component: ListComponent) : Child()
        class DetailsChild(val component: DetailsComponent) : Child()
    }


    /**
     * Фабрика для создания компонента
     */
    fun interface Factory {

        /**
         * Создание компонента
         * @param componentContext Контекст компонента
         */
        operator fun invoke(componentContext: ComponentContext): RootComponent
    }
}

internal class DefaultRootComponent(
    componentContext: ComponentContext,
    private val homeComponent: HomeComponent.Factory,
    private val listComponent: ListComponent.Factory,
    private val detailsComponent: DetailsComponent.Factory,
) : RootComponent, BaseComponent(componentContext) {
    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Home,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.Home -> RootComponent.Child.HomeChild(
                homeComponent(
                    componentContext = componentContext,
                    onGoToList = {
                        navigation.push(Config.List)
                    }
                )
            )
            is Config.Details -> RootComponent.Child.DetailsChild(
                detailsComponent(
                    componentContext = componentContext,
                    item = config.item,
                )
            )
            is Config.List -> RootComponent.Child.ListChild(
                listComponent(
                    componentContext = componentContext,
                    onGoToDetails = { item ->
                        navigation.push(Config.Details(item))
                    }
                )
            )
        }


    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Home : Config

        @Serializable
        data object List : Config

        @Serializable
        data class Details(val item: Item) : Config
    }

    class Factory(
        private val homeComponent: HomeComponent.Factory,
        private val listComponent: ListComponent.Factory,
        private val detailsComponent: DetailsComponent.Factory,
    ) : RootComponent.Factory {
        override fun invoke(componentContext: ComponentContext): RootComponent {
            return DefaultRootComponent(
                componentContext = componentContext,
                homeComponent = homeComponent,
                listComponent = listComponent,
                detailsComponent = detailsComponent,
            )
        }
    }
}