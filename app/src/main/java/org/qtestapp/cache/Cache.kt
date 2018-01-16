package org.qtestapp.cache

import java.io.File


interface Cache<K, V> {
    val cacheDirectory: File
    val policy: CachePolicy
    fun put(key: K, value: V, listener: CacheResultCallback)
    fun remove(key: K, listener: CacheResultCallback)

    interface CacheResultCallback {
        fun onStart()
        fun onSuccess()
        fun onFailure(error: Throwable)
    }
}