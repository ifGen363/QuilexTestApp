package org.qtestapp.cache

import java.io.File
import java.io.InputStream

/**
 * Created by ifgen on 08.01.2018.
 */
interface CachePolicy {
    fun save(inputStream: InputStream, file: File)
    //fun read(file: File)
    fun delete(file: File)
    fun clear(directory: File)
}