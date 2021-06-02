package com.example.socketweatherdemo.list

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.viewModelScope
import com.example.socketweatherdemo.appSingletons
import com.example.socketweatherdemo.list.ListEvent.ClickItem
import com.example.socketweatherdemo.R
import com.example.socketweatherdemo.ScopedController
import com.example.socketweatherdemo.inflate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ListController(
    args: Bundle
) : ScopedController<ListViewModel, ListPresenter>(args) {

    lateinit var context: Context

    constructor(name: String = "") :
            this(bundleOf("name" to name))

    override fun onCreateView(container: ViewGroup): View =
        container.inflate(R.layout.controller_list)

    override fun onCreateViewModel(context: Context): ListViewModel {
        this.context = context
        Toast.makeText(context, "${args["name"]}", Toast.LENGTH_SHORT).show()
        return ListViewModel(
            context.appSingletons.networkComponents.api,
            context as Application
        )
    }

    override fun onCreatePresenter(view: View, viewModel: ListViewModel) =
        ListPresenter(view)

    override fun onAttach(
        view: View,
        presenter: ListPresenter,
        viewModel: ListViewModel,
        viewScope: CoroutineScope
    ) {
        viewModel.stateFlow
            .onEach { presenter.display(it) }
            .launchIn(viewScope)
        presenter.eventFlow
            .onEach { event ->
                when (event) {
                    is ClickItem -> {
                        Toast.makeText(
                            context, "${event.listResponse}", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            .launchIn(viewScope)
    }

    override fun onDestroy(viewModel: ListViewModel?) {
        viewModel?.viewModelScope?.cancel()
    }
}
