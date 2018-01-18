package org.qtestapp.loader

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File


interface Source<S> {
    val source: S
}

class UrlSource(override val source: String) : Source<String>

class FileSource(override val source: File) : Source<File>


interface GifLoader<T : Source<*>> {
    fun loadToView(view: ImageView, from: T)
}


class UrlGifLoader : GifLoader<UrlSource> {

    override fun loadToView(view: ImageView, from: UrlSource) {
        Glide
                .with(view.context)
                .load(from.source)
                .into(view)
    }
}

class FileGifLoader : GifLoader<FileSource> {

    override fun loadToView(view: ImageView, from: FileSource) {
        Glide
                .with(view.context)
                .load(from.source)
                .into(view)
    }
}
