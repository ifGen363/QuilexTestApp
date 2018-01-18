package org.qtestapp.adapters

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import org.qtestapp.R
import org.qtestapp.cache.GifCache
import org.qtestapp.loader.GifLoader
import org.qtestapp.rest.model.response.GifData


interface ItemConfiguration {
    fun configureItem(actionView: View, gifView: ImageView, gifCache: GifCache, gifLoader: GifLoader, gifData: GifData)
    var action: CacheAction
}

abstract class DefaultItemConfiguration : ItemConfiguration {

    private lateinit var cacheAction: CacheAction

    override var action: CacheAction
        get() = cacheAction
        set(value) {
            cacheAction = value
        }

    override fun configureItem(actionView: View, gifView: ImageView, gifCache: GifCache, gifLoader: GifLoader, gifData: GifData) {
        actionView.setOnClickListener {
            action.execute(gifCache, gifData)
        }
    }
}

class LikeActionItemConfiguration : DefaultItemConfiguration() {

    override fun configureItem(actionView: View, gifView: ImageView, gifCache: GifCache, gifLoader: GifLoader, gifData: GifData) {

        gifData.url?.let { gifLoader.loadToView(gifView, it) }

        if (gifCache.isInGifInCache(gifData)) {
            actionView.background = ContextCompat.getDrawable(actionView.context, R.drawable.like_background)
            action = DeleteAction(GifCacheResultCallback())
        } else {
            actionView.background = ContextCompat.getDrawable(actionView.context, R.drawable.ignore_background)
            action = SaveAction(GifCacheResultCallback())
        }

        super.configureItem(actionView, gifView, gifCache, gifLoader, gifData)
    }
}


class DeleteActionItemConfiguration : DefaultItemConfiguration() {

    override fun configureItem(actionView: View, gifView: ImageView, gifCache: GifCache, gifLoader: GifLoader, gifData: GifData) {

        gifCache.get(gifData)?.let { gifLoader.loadToView(gifView, it) }

        actionView.background = ContextCompat.getDrawable(actionView.context, R.drawable.ic_delete_black_24dp)
        action = DeleteAction(GifCacheResultCallback())

        super.configureItem(actionView, gifView, gifCache, gifLoader, gifData)
    }
}


interface CacheAction {
    fun execute(gifCache: GifCache, gifData: GifData)
}


class SaveAction(private val cacheResult: GifCache.CacheResultCallback) : CacheAction {

    override fun execute(gifCache: GifCache, gifData: GifData) {
        gifCache.put(gifData, cacheResult)
    }
}

class DeleteAction(private val cacheResult: GifCache.CacheResultCallback) : CacheAction {

    override fun execute(gifCache: GifCache, gifData: GifData) {
        gifCache.remove(gifData, cacheResult)
    }
}


class GifCacheResultCallback : GifCache.CacheResultCallback {

    override fun onSuccess() {
        System.out.print("Success")
    }

    override fun onFailure(error: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}