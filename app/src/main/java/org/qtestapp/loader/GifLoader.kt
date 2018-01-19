package org.qtestapp.loader

import android.widget.ImageView
import com.bumptech.glide.Glide
import org.qtestapp.cache.GifCache
import org.qtestapp.rest.model.response.GifData


class GifLoader(private val gifCache: GifCache) {

    fun loadToView(view: ImageView, gifData: GifData) {

        if (gifCache.isGifInCache(gifData)) {
            loadByGlide(view, gifCache.getFile(gifData))
        } else {
            loadByGlide(view, gifData.url)
        }
    }

    private fun loadByGlide(view: ImageView, from: Any?) {

        Glide
                .with(view.context)
                .load(from)
                .into(view)
    }
}
