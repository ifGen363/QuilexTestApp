package org.qtestapp.cache

import java.io.File

/**
 * Created by MartulEI on 04.01.2018.
 */
interface Cache<K, V : CacheValue> {
    val cacheDirectory: File
    fun get(key: K): V?
    fun put(key: K, value: V): Boolean
    fun remove(key: K)
    fun clear()
}

interface CacheValue {
    val file: File
}