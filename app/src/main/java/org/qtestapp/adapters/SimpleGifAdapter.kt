package org.qtestapp.adapters

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import org.qtestapp.R
import org.qtestapp.cache.GifCache
import org.qtestapp.loader.GifLoader
import org.qtestapp.rest.model.response.GifData


class SimpleGifAdapter(@LayoutRes private val layoutRes: Int,
                       private val gifCache: GifCache,
                       private val gifLoader: GifLoader,
                       private val itemConfiguration: ItemConfiguration)
    : RecyclerView.Adapter<SimpleGifViewHolder>() {

    init {
        gifCache.addStateListener(object : GifCache.CacheStateListener {
            override fun onStateChanged(gifData: GifData) {
                val index = gifs.indexOf(gifData)
                if (index != -1) {
                    this@SimpleGifAdapter.notifyItemChanged(index)
                }
            }
        })
    }

    private var gifs: MutableList<GifData> = ArrayList()

    fun resetData(newData: List<GifData>) {
        gifs.clear()
        gifs.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SimpleGifViewHolder?, position: Int) {
        holder?.run {
            configure(gifCache, gifs[position], gifLoader)
        }
    }

    override fun getItemCount(): Int = gifs.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SimpleGifViewHolder = SimpleGifViewHolder(LayoutInflater.from(parent?.context).inflate(layoutRes, parent, false),
                                                                                                                  itemConfiguration)
}

class SimpleGifViewHolder(itemView: View,
                          private val itemConfig: ItemConfiguration) : RecyclerView.ViewHolder(itemView) {

    private val gifImageView: ImageView = itemView.findViewById(R.id.gifImageView)
    private val actionView: ImageView = itemView.findViewById(R.id.actionView)


    fun configure(gifCache: GifCache, gifData: GifData, gifLoader: GifLoader) {
        itemConfig.configureItem(actionView, gifImageView, gifCache, gifLoader, gifData)
    }
}


