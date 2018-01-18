package org.qtestapp.adapters

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import org.qtestapp.R
import org.qtestapp.cache.Cache
import org.qtestapp.cache.GifCache
import org.qtestapp.loader.*
import org.qtestapp.rest.model.response.GifData


interface ItemConfiguration<out T : GifLoader<out Source<*>>> {
    val loader: T
    fun configureItem(actionView: View, gifCache: GifCache, gifData: GifData)
    fun showGif(gifView: ImageView, gifData: GifData, gifCache: GifCache)
    var action: CacheAction
}

abstract class DefaultItemConfiguration<out T : GifLoader<out Source<*>>> : ItemConfiguration<T> {

    private lateinit var cacheAction: CacheAction

    override var action: CacheAction
        get() = cacheAction
        set(value) {
            cacheAction = value
        }

    override fun configureItem(actionView: View, gifCache: GifCache, gifData: GifData) {
        actionView.setOnClickListener {
            action.execute(gifCache, gifData)
        }
    }
}

class LikeActionItemConfiguration(override val loader: UrlGifLoader) : DefaultItemConfiguration<UrlGifLoader>() {

    override fun configureItem(actionView: View, gifCache: GifCache, gifData: GifData) {
        if (gifCache.isInGifInCache(gifData)) {
            actionView.background = ContextCompat.getDrawable(actionView.context, R.drawable.like_background)
            action = DeleteAction(GifCacheResultCallback())
        } else {
            actionView.background = ContextCompat.getDrawable(actionView.context, R.drawable.ignore_background)
            action = SaveAction(GifCacheResultCallback())
        }

        super.configureItem(actionView, gifCache, gifData)
    }

    override fun showGif(gifView: ImageView, gifData: GifData, gifCache: GifCache) {
        gifData.url?.let { loader.loadToView(gifView, UrlSource(it)) }
    }
}


class DeleteActionItemConfiguration(override val loader: FileGifLoader) : DefaultItemConfiguration<FileGifLoader>() {


    override fun configureItem(actionView: View, gifCache: GifCache, gifData: GifData) {
        actionView.background = ContextCompat.getDrawable(actionView.context, R.drawable.ic_delete_black_24dp)
        action = DeleteAction(GifCacheResultCallback())

        super.configureItem(actionView, gifCache, gifData)
    }

    override fun showGif(gifView: ImageView, gifData: GifData, gifCache: GifCache) {
        gifCache.get(gifData)?.let { loader.loadToView(gifView, FileSource(it)) }
    }
}


interface CacheAction {
    fun execute(gifCache: GifCache, gifData: GifData)
}


class SaveAction(private val cacheResult: Cache.CacheResultCallback) : CacheAction {

    override fun execute(gifCache: GifCache, gifData: GifData) {
        gifCache.put(gifData, cacheResult)
    }
}

class DeleteAction(private val cacheResult: Cache.CacheResultCallback) : CacheAction {

    override fun execute(gifCache: GifCache, gifData: GifData) {
        gifCache.remove(gifData, cacheResult)
    }
}


class GifCacheResultCallback : Cache.CacheResultCallback {
    override fun onStart() {
        System.out.print("Start")
    }

    override fun onSuccess() {
        System.out.print("Success")
    }

    override fun onFailure(error: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}