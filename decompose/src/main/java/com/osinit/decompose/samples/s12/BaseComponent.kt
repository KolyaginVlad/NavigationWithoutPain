package com.osinit.decompose.samples.s12

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseComponent(
    componentContext: ComponentContext,
): ComponentContext by componentContext  {

    private val coroutineScopeHolder = instanceKeeper.getOrCreate { CoroutineScopeHolder(Dispatchers.Main) }

    val componentScope
        get() = coroutineScopeHolder.scope

    private class CoroutineScopeHolder(mainContext: CoroutineContext) : InstanceKeeper.Instance {
        // The scope survives Android configuration changes
        val scope = CoroutineScope(mainContext + SupervisorJob())

        override fun onDestroy() {
            scope.cancel() // Cancel the scope when the instance is destroyed
        }
    }
}