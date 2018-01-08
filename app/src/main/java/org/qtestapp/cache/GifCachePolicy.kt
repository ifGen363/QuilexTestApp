package org.qtestapp.cache

import java.io.*

/**
 * Created by ifgen on 08.01.2018.
 */
class GifCachePolicy : CachePolicy {

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

            println("Done!")

        } catch (e: IOException) {
            throw IOException("Something went wrong while writing fil: " + file)
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

    override fun delete(file: File) {
        file.delete()
    }

    override fun clear(directory: File) {
        if (directory.isDirectory) {
            val files = directory.listFiles()
            files.forEach { it.delete() }
        }
    }
}