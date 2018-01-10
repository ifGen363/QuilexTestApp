package org.qtestapp.adapters.holders

import android.content.Context
import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File
import java.net.URI


open class BaseGifViewHolder(itemView: View, @IdRes gifImageViewId: Int) : RecyclerView.ViewHolder(itemView) {

    protected val gifImageView: ImageView? = itemView.findViewById(gifImageViewId)

    fun loadGifFromUrl(context: Context, url: String) {
        Glide
                .with(context)
                .load(url)
                .into(gifImageView)
    }

    fun loadGifFromCache(context: Context, uri: URI) {
        Glide
                .with(context)
                .load(File(uri))
                .into(gifImageView)
    }
}