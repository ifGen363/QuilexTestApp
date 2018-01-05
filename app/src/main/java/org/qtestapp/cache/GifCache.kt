package org.qtestapp.cache

import java.io.File

/**
 * Created by MartulEI on 04.01.2018.
 */
class GifCache(override val cacheDirectory: File) : Cache<String, GifCacheValue> {

    private var cachedFiles: LinkedHashMap<String, GifCacheValue> = LinkedHashMap()

    init {
        cacheDirectory.mkdirs()

    }

    override fun get(key: String): GifCacheValue? {
        return cachedFiles[key]
    }

    override fun put(key: String, value: GifCacheValue): Boolean {
        cachedFiles.put(key, value)
        //key.hashCode()
        return true
    }

    override fun remove(key: String) {

    }

    override fun clear() {

    }
}

class GifCacheValue(override val file: File) : CacheValue