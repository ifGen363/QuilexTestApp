package org.qtestapp.cache

import org.jetbrains.anko.doAsync
import org.qtestapp.rest.model.response.GifData
import java.io.File
import java.net.URL


class GifCache private constructor(private val cacheDirectory: File,
                                   private val policy: CachePolicy) {

    private val cachedGifs: MutableList<GifData>

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
        cachedGifs = getCachedGifData().toMutableList()
    }

    fun getFile(value: GifData): File? = File(cacheDirectory, value.id)

    fun getAllCachedGifs() = ArrayList<GifData>(cachedGifs)

    fun put(value: GifData, listener: CacheResultCallback) {
        val file = File(cacheDirectory, value.id)
        try {
            doAsync {
                policy.save(URL(value.url).openStream(), file)
            }
        } catch (ex: CacheIOException) {
            ex.printStackTrace()
            listener.onFailure(ex)
        }
        cachedGifs.add(value)
        listener.onSuccess()
    }

    fun remove(value: GifData, listener: CacheResultCallback) {
        try {
            getFile(value)?.let { policy.delete(it) }
        } catch (ex: CacheIOException) {
            ex.printStackTrace()
            listener.onFailure(ex)
        }
        cachedGifs.remove(value)
        listener.onSuccess()
    }

    fun isGifInCache(value: GifData): Boolean = File(cacheDirectory, value.id).exists()

    private fun getCachedGifData() = getAllFileNames().map { GifData(it) }

    private fun getAllFileNames() = cacheDirectory.listFiles().map { it.name }

    interface CacheResultCallback {
        fun onSuccess()
        fun onFailure(error: Throwable)
    }
}