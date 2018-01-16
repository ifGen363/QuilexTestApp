package org.qtestapp.loader

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File


interface GifLoader {
    fun loadToView(view: ImageView, source: Any?)
}

class UrlGifLoader : GifLoader {

    override fun loadToView(view: ImageView, source: Any?) {
        if (source is String) {
            Glide
                    .with(view.context)
                    .load(source)
                    .into(view)
        } else {
            throw RuntimeException("Source is not an url string")
        }
    }
}

class FileGifLoader : GifLoader {

    override fun loadToView(view: ImageView, source: Any?) {
        if (source is File) {
            Glide
                    .with(view.context)
                    .load(source)
                    .into(view)
        } else {
            throw RuntimeException("Source is not a file")
        }
    }
}
