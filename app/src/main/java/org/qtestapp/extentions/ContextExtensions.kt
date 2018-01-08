package org.qtestapp.extentions

import android.content.Context
import android.net.ConnectivityManager
import java.io.File

fun Context.isOnline(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo
    return netInfo != null && netInfo.isConnectedOrConnecting
}

fun Context.getCacheDirectory(): File {
    val file = File(cacheDir.path + "/gifs/")
    file.mkdirs()
    return file
}