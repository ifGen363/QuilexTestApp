package org.qtestapp.cache

import java.io.*


class GifCachePolicy : CachePolicy {

    @Throws(CacheIOException::class)
    override fun save(inputStream: InputStream, file: File) {

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
    override fun delete(file: File) {
        if (!file.delete()) {
            throw CacheIOException("Something went wrong while deleting file: " + file)
        }
    }
}

class CacheIOException(message: String) : Exception(message)