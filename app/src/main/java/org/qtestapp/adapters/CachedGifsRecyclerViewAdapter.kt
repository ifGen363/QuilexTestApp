package org.qtestapp.adapters

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup
import org.qtestapp.R
import org.qtestapp.adapters.holders.CachedGifItemViewHolder
import org.qtestapp.cache.GifCache


class CachedGifsRecyclerViewAdapter(
        val context: Context,
        @LayoutRes private val layoutRes: Int,
        private val gifCache: GifCache) : BaseRecyclerViewAdapter<CachedGifItemViewHolder, String>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CachedGifItemViewHolder =
            CachedGifItemViewHolder(LayoutInflater.from(parent?.context).inflate(layoutRes, parent, false),
                                    R.id.gifImageView)


    override fun onBindViewHolder(holder: CachedGifItemViewHolder?, position: Int) {
        holder?.let {
            with(it) {
                deleteImageView.setOnClickListener {
                    val pos: Int = adapterPosition
                    gifCache.remove(data[pos])
                    data.removeAt(pos)
                    notifyItemRemoved(pos)
                }

                gifCache.get(data[position])?.file?.toURI()?.let { uri -> loadGifFromCache(context, uri) }
            }
        }
    }
}