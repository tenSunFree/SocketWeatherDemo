package com.example.socketweatherdemo

import android.os.StrictMode
import com.squareup.moshi.Types
import okio.buffer
import okio.sink
import okio.source
import java.io.File

/**
 * Explicitly perform disk operations that would normally violate [StrictMode].
 */
inline fun allowMainThreadDiskOperations(block: () -> Unit) {
    val diskReadPolicy = StrictMode.allowThreadDiskReads()
    val diskWritePolicy = StrictMode.allowThreadDiskWrites()
    block()
    StrictMode.setThreadPolicy(diskReadPolicy)
    StrictMode.setThreadPolicy(diskWritePolicy)
}
