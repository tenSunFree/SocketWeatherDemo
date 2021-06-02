package com.example.socketweatherdemo.common.component

import com.example.socketweatherdemo.list.ListApi
import kotlinx.coroutines.flow.Flow

interface NetworkComponents {

  val api: ListApi

  /**
   * Emits whenever there's a fundamental change in environment config.
   * (Like changing between mock and real endpoints in debug builds).
   */
  val environmentChanges: Flow<Unit>
}
