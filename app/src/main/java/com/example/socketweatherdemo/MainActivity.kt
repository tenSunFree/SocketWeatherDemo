package com.example.socketweatherdemo

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.socketweatherdemo.list.ListController
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.example.socketweatherdemo.common.component.CurrentBuildTypeComponents

class MainActivity : AppCompatActivity() {

    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRouter(savedInstanceState)
    }

    private fun initRouter(savedInstanceState: Bundle?) {
        val rootContainer: ViewGroup =
            CurrentBuildTypeComponents.createRootContainerFor(this)
        router = Conductor.attachRouter(this, rootContainer, savedInstanceState)
        router.addChangeListener(ControllerLeakListener)
        if (!router.hasRootController()) {
            router.setRoot(ListController(name = "list").asTransaction())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        router.removeChangeListener(ControllerLeakListener)
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            finishAfterTransition()
        }
    }
}