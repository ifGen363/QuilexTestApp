package org.qtestapp.cache

import java.io.File
import java.io.InputStream


interface CachePolicy {
    fun save(inputStream: InputStream, file: File)
    //fun read(file: File)
    fun delete(file: File)
    fun clear(directory: File)
}