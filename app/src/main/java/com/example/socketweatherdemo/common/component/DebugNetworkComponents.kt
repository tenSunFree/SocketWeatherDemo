package com.example.socketweatherdemo.common.component

import com.example.socketweatherdemo.list.ListApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class DebugNetworkComponents : NetworkComponents {

    companion object {
        const val API_ENDPOINT = "https://jsonplaceholder.typicode.com/"
    }

    override val api: ListApi = Retrofit.Builder()
        .baseUrl(API_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create()

    // Environment never changes in release builds.
    override val environmentChanges: Flow<Unit> = emptyFlow()
}