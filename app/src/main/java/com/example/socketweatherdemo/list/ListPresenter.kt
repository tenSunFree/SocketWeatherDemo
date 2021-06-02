package com.example.socketweatherdemo.list

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.socketweatherdemo.R
import com.example.socketweatherdemo.list.ListState.Status.Error
import com.example.socketweatherdemo.list.ListState.Status.Loading
import com.example.socketweatherdemo.list.ListState.Status.Success
import com.example.socketweatherdemo.list.ListEvent.ClickItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

class ListPresenter(view: View) {

    private val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
    private val errorTextView: TextView = view.findViewById(R.id.text_view_error)
    private val progressBar: View = view.findViewById(R.id.progress_bar)
    private val adapter = ListAdapter()
    val eventFlow: Flow<ListEvent>

    init {
        recyclerView.adapter = adapter
        eventFlow = merge(
            adapter.clicksFlow().map { ClickItem(it) },
        )
    }

    fun display(state: ListState) {
        recyclerView.isVisible =
            state.status == Success && state.results.isNotEmpty()
        errorTextView.apply {
            isVisible = state.status == Error
            text = state.error
        }
        progressBar.isVisible = state.status == Loading
        adapter.set(state.results)
    }
}
