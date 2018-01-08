package org.qtestapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import com.bumptech.glide.Glide
import org.qtestapp.R
import org.qtestapp.cache.GifCache
import java.io.File
import java.net.URI


class GifsRecyclerViewAdapter(private val context: Context,
                              private val layoutRes: Int,
                              private val gifCache: GifCache,
                              var cacheAction: () -> Unit = {}) :
        BaseRecyclerViewAdapter<GifsRecyclerViewAdapter.GifItemViewHolder, String>() {


    override fun onBindViewHolder(holder: GifItemViewHolder?, position: Int) {
        with(holder) {
            this?.let {
                it.isCachedCheckBox.setOnCheckedChangeListener { _, isChecked ->
                    if(isChecked) {
                        cacheAction()
                    }
                }

                gifCache.get(data[position])?.file?.let {
                    isCachedCheckBox.isChecked = true
                    loadGifFromCache(context, it.toURI())
                } ?: run {
                    isCachedCheckBox.isChecked = false
                    loadGifFromUrl(context, data[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GifItemViewHolder =
            GifItemViewHolder(LayoutInflater.from(parent?.context).inflate(layoutRes, parent, false))


    class GifItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val gifImageView: ImageView = itemView.findViewById(R.id.gifImageView)
        val isCachedCheckBox: CheckBox = itemView.findViewById(R.id.isCachedCheckBox)

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
}