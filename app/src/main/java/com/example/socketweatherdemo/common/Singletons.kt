package com.example.socketweatherdemo

import android.app.Application
import android.content.Context
import androidx.annotation.MainThread
import com.example.socketweatherdemo.common.component.CurrentBuildTypeComponents
import com.example.socketweatherdemo.common.component.NetworkComponents

/**
 * A cheap and dirty service locator that houses singletons used throughout
 * the app.
 */
interface Singletons {
    val networkComponents: NetworkComponents
}

private var singletons: Singletons? = null

// Although this technically doesn't need to be an extension on Context, it's
// useful to limit access to service locators to when only a Context is available.
@Suppress("unused")
val Context.appSingletons
    get() = checkNotNull(singletons) { "initialiseSingletons() must be called in Application." }

@MainThread
fun Application.initialiseSingletons() {
    singletons = SingletonCache(this)
}

private class SingletonCache(app: Application) : Singletons {
    override val networkComponents =
        CurrentBuildTypeComponents.createNetworkComponents(app)
}
