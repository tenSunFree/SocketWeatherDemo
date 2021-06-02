package com.example.socketweatherdemo.common.component

import android.app.Activity
import android.app.Application
import android.view.ViewGroup
import androidx.annotation.MainThread
import com.example.socketweatherdemo.common.component.NetworkComponents

/**
 * Generators for things that change depending on the build type being debug or release.
 */
@MainThread
interface BuildTypeComponents {
    fun createRootContainerFor(activity: Activity): ViewGroup
    fun createNetworkComponents(app: Application): NetworkComponents
}
