package org.qtestapp.cache

import android.os.Handler
import android.os.Looper
import org.jetbrains.anko.doAsync
import org.qtestapp.rest.model.response.GifData
import java.io.*
import java.net.URL


class GifCache private constructor(private val cacheDirectory: File) {

    private val cachedGifs: MutableList<GifData>
    private var stateListener: CacheStateListener? = null

    companion object {

        private var instance: GifCache? = null

        fun getInstance(cacheDirectory: File): GifCache {

            if (instance == null) {
                instance = GifCache(cacheDirectory)
            }
            return instance as GifCache
        }
    }

    init {
        cachedGifs = getCachedGifData().toMutableList()
    }

    fun addStateListener(listener: CacheStateListener) {
        stateListener = listener
    }

    fun getFile(value: GifData): File? = File(cacheDirectory, value.id)

    fun getAllCachedGifs() = ArrayList<GifData>(cachedGifs)

    fun put(value: GifData, listener: CacheResultCallback) {
        val file = File(cacheDirectory, value.id)
        try {
            doAsync {
                saveFile(URL(value.url).openStream(), file)
                Handler(Looper.getMainLooper()).post {
                    cachedGifs.add(value)
                    listener.onSuccess()
                    stateListener?.onStateChanged(value)
                }
            }
        } catch (ex: CacheIOException) {
            ex.printStackTrace()
            listener.onFailure(ex)
        }
    }

    fun remove(value: GifData, listener: CacheResultCallback) {
        try {
            doAsync {
                getFile(value)?.let { deleteFile(it) }
                Handler(Looper.getMainLooper()).post {
                    cachedGifs.remove(value)
                    listener.onSuccess()
                    stateListener?.onStateChanged(value)
                }
            }
        } catch (ex: CacheIOException) {
            ex.printStackTrace()
            listener.onFailure(ex)
        }
    }

    fun isGifInCache(value: GifData): Boolean = File(cacheDirectory, value.id).exists()

    private fun getCachedGifData() = getAllFileNames().map { GifData(it) }

    private fun getAllFileNames() = cacheDirectory.listFiles().map { it.name }


    @Throws(CacheIOException::class)
    private fun saveFile(inputStream: InputStream, file: File) {

        var outputStream: OutputStream? = null

        try {
            file.createNewFile()
            outputStream = FileOutputStream(file)

            var read = 0
            val bytes = ByteArray(1024)

            while (true) {
                read = inputStream.read(bytes)
                if (read != -1) {
                    outputStream.write(bytes, 0, read)
                } else break
            }

        } catch (e: IOException) {
            e.printStackTrace()
            throw CacheIOException("Something went wrong while writing file: " + file)
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @Throws(CacheIOException::class)
    private fun deleteFile(file: File) {
        if (!file.delete()) {
            throw CacheIOException("Something went wrong while deleting file: " + file)
        }
    }

    interface CacheResultCallback {
        fun onSuccess()
        fun onFailure(error: Throwable)
    }

    interface CacheStateListener {
        fun onStateChanged(gifData: GifData)
    }
}

class CacheIOException(message: String) : IOException(message)