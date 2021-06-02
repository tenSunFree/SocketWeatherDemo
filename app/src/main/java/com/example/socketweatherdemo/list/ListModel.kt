package com.example.socketweatherdemo.list

import com.example.socketweatherdemo.list.ListState.Status.Loading

data class ListState(
    val status: Status = Loading,
    val results: List<ListResponse> = emptyList(),
    val error: String = ""
) {
    enum class Status { Loading, Success, Error }
}

sealed class ListEvent {
    data class ClickItem(val listResponse: ListResponse) : ListEvent()
}

data class ListResponse(
    val userId: Int?,
    val id: Int?,
    val title: String?,
    val body: String?
)
