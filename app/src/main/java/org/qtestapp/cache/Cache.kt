package org.qtestapp.cache

import java.io.File
import java.io.InputStream


interface Cache<K, out V : CacheValue> {
    val cacheDirectory: File
    val policy: CachePolicy
    fun get(key: K): V?
    fun put(key: K, inputStream: InputStream)
    fun remove(key: K)
    fun clear()
}

interface CacheValue {
    val file: File
}