package org.qtestapp.cache

import java.io.File
import java.io.InputStream

/**
 * Created by MartulEI on 04.01.2018.
 */
class GifCache(override val cacheDirectory: File, override val policy: CachePolicy) : Cache<String, GifCacheValue> {

    private var cachedFiles: HashMap<Int, GifCacheValue> = HashMap()

    init {
        val files = cacheDirectory.listFiles()
        files.forEach { file ->
            file.name.toIntOrNull()?.let {
                cachedFiles.put(it, GifCacheValue(file))
            }
        }
    }

    override fun get(key: String): GifCacheValue? {
        return cachedFiles[key.hashCode()]
    }

    override fun put(key: String, inputStream: InputStream) {
        val file = File(cacheDirectory, key.hashCode().toString())
        policy.save(inputStream, file)
        cachedFiles.put(key.hashCode(), GifCacheValue(file))
    }

    override fun remove(key: String) {
        get(key)?.file?.let { policy.delete(it) }
        cachedFiles.remove(key.hashCode())
    }

    override fun clear() {
        policy.clear(cacheDirectory)
        cachedFiles.clear()
    }
}

class GifCacheValue(override val file: File) : CacheValue