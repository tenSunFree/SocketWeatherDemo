package com.example.socketweatherdemo.common.component

import android.app.Activity
import android.app.Application
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.MainThread
import com.bluelinelabs.conductor.ChangeHandlerFrameLayout

object CurrentBuildTypeComponents : BuildTypeComponents by DebugBuildComponents

@MainThread
private object DebugBuildComponents : BuildTypeComponents {

    @MainThread
    override fun createRootContainerFor(activity: Activity): ViewGroup {
        Log.d("more", "ReleaseBuildComponents, createRootContainerFor")
        val content = activity.findViewById(android.R.id.content) as ViewGroup
        val container = ChangeHandlerFrameLayout(activity)
        content.addView(container)
        return container
    }

    @MainThread
    override fun createNetworkComponents(app: Application): NetworkComponents {
        return DebugNetworkComponents()
    }
}
