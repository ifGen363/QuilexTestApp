package org.qtestapp.adapters

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup
import org.qtestapp.R
import org.qtestapp.adapters.holders.GifItemViewHolder
import org.qtestapp.cache.GifCache
import org.qtestapp.rest.model.response.GifData


class GifsRecyclerViewAdapter(private val context: Context,
                              @LayoutRes private val layoutRes: Int,
                              private val gifCache: GifCache,
                              private var cacheAction: (id: String, url: String) -> Unit) :
        BaseRecyclerViewAdapter<GifItemViewHolder, GifData>() {


    override fun onBindViewHolder(holder: GifItemViewHolder?, position: Int) {
        holder?.let {
            with(it) {

                val id: String = data[position].id
                val url: String = data[position].images?.fixedHeightDownsampled?.url!!

                gifCache.get(id)?.file?.let {
                    isCachedCheckBox.isChecked = true
                    loadGifFromCache(context, it.toURI())
                } ?: run {
                    isCachedCheckBox.isChecked = false
                    loadGifFromUrl(context, url)
                }

                it.isCachedCheckBox.setOnClickListener {
                    if (isCachedCheckBox.isChecked) {
                        cacheAction(id, url)
                    } else {
                        gifCache.remove(id)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GifItemViewHolder =
            GifItemViewHolder(LayoutInflater.from(parent?.context).inflate(layoutRes, parent, false),
                              R.id.gifImageView)

}