package com.example.socketweatherdemo.list

import com.example.socketweatherdemo.list.ListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ListApi {

    @GET("posts")
    suspend fun searchForLocation(): List<ListResponse>
}
