package org.qtestapp.cache

import org.jetbrains.anko.doAsync
import org.qtestapp.rest.model.response.GifData
import java.io.File
import java.net.URL
import java.util.*


class GifCache private constructor(private val cacheDirectory: File,
                                   private val policy: CachePolicy) {

    private var cachedFiles: HashMap<String, File> = HashMap()

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
            cachedFiles.put(file.name, file)
        }
    }

    private fun getAllFileNames() = cacheDirectory.listFiles().map { it.name }


    private fun put(key: String, value: GifData, listener: CacheResultCallback) {
        val file = File(cacheDirectory, value.id)
        try {
            doAsync {
                policy.save(URL(value.url).openStream(), file)
            }
        } catch (ex: CacheIOException) {
            ex.printStackTrace()
            listener.onFailure(ex)
        }
        cachedFiles.put(key, file)
        listener.onSuccess()
    }

    private fun remove(key: String, listener: CacheResultCallback) {
        try {
            get(key)?.let { policy.delete(it) }
        } catch (ex: CacheIOException) {
            ex.printStackTrace()
            listener.onFailure(ex)
        }
        cachedFiles.remove(key)
        listener.onSuccess()
    }


    private fun get(key: String): File? = cachedFiles[key]

    fun get(value: GifData): File? = get(value.id)

    fun put(value: GifData, listener: CacheResultCallback) {
        put(value.id, value, listener)
    }

    fun remove(gifData: GifData, listener: CacheResultCallback) {
        remove(gifData.id, listener)
    }

    fun isInGifInCache(gifData: GifData): Boolean = get(gifData.id)?.let { true } ?: run { false }

    fun getCachedGifData() = getAllFileNames().map { GifData(it) }


    interface CacheResultCallback {
        fun onSuccess()
        fun onFailure(error: Throwable)
    }
}