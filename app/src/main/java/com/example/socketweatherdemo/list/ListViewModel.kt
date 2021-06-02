package com.example.socketweatherdemo.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.socketweatherdemo.list.ListState.Status
import com.example.socketweatherdemo.list.ListState.Status.Success
import com.example.socketweatherdemo.isNetWorkAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel(
    private val api: ListApi,
    app: Application,
) : AndroidViewModel(app) {

    private val state = ListState()
    private val mutableStateFlow = MutableStateFlow(state)
    val stateFlow: StateFlow<ListState> = mutableStateFlow

    init {
        if (app.isNetWorkAvailable) {
            viewModelScope.launch { mutableStateFlow.value = getData() }
        } else {
            mutableStateFlow.value = ListState(status = Status.Error)
        }
    }

    private suspend fun getData(): ListState = try {
        val results = withContext(Dispatchers.IO) { api.searchForLocation() }
        mutableStateFlow.value.copy(results = results, status = Success)
    } catch (e: Exception) {
        mutableStateFlow.value.copy(status = Status.Error, error = e.message.toString())
    }
}
