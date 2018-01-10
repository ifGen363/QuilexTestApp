package org.qtestapp.cache

import java.io.File
import java.io.InputStream

/**
 * Created by MartulEI on 04.01.2018.
 */
class GifCache private constructor(override val cacheDirectory: File,
                                   override val policy: CachePolicy) : Cache<String, GifCacheValue> {

    private var cachedFiles: HashMap<String, GifCacheValue> = HashMap()

    companion object {

        private var instance: GifCache? = null

        fun getInstance(cacheDirectory: File,
                        policy: CachePolicy): GifCache {
            if (instance == null) {
                instance = GifCache(cacheDirectory, policy)
            }
            return instance as GifCache
        }
    }

    init {
        val files = cacheDirectory.listFiles()
        files.forEach { file ->
            cachedFiles.put(file.name, GifCacheValue(file))
        }
    }

    fun getAllFileNames() = cacheDirectory.listFiles().map { it.name }

    override fun get(key: String): GifCacheValue? {
        return cachedFiles[key]
    }

    override fun put(key: String, inputStream: InputStream) {
        val file = File(cacheDirectory, key)
        policy.save(inputStream, file)
        cachedFiles.put(key, GifCacheValue(file))
    }

    override fun remove(key: String) {
        get(key)?.file?.let { policy.delete(it) }
        cachedFiles.remove(key)
    }

    override fun clear() {
        policy.clear(cacheDirectory)
        cachedFiles.clear()
    }
}

class GifCacheValue(override val file: File) : CacheValue