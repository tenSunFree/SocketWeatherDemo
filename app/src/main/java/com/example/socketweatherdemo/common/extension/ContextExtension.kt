package com.example.socketweatherdemo

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.appcompat.content.res.AppCompatResources

val Context.app: Application
    get() = applicationContext as Application

@StyleRes
fun Context.resolveAttr(@AttrRes attrId: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrId, typedValue, true)
    return typedValue.resourceId
}

@ColorInt
fun Context.getThemeColour(@AttrRes attrId: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrId, typedValue, true)
    val colourRes: Int =
        if (typedValue.resourceId != 0) typedValue.resourceId
        else typedValue.data

    return getColor(colourRes)
}

fun Context.requireDrawable(@DrawableRes resId: Int): Drawable {
    return requireNotNull(AppCompatResources.getDrawable(this, resId)) {
        "Required Drawable is null."
    }
}

val Context.isNetWorkAvailable: Boolean
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val result: Boolean
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val networkCapabilities: NetworkCapabilities? =
                connectivityManager.getNetworkCapabilities(network)
            result = if (networkCapabilities != null) {
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("khb", "wifi")
                } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("khb", "Mobile Data")
                }
                true
            } else {
                Log.i("khb", "No internet")
                false
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            result = if (networkInfo != null && networkInfo.isAvailable) {
                Log.i("khb", networkInfo.typeName)
                true
            } else {
                Log.i("khb", "No internet")
                false
            }
        }
        return result
    }
