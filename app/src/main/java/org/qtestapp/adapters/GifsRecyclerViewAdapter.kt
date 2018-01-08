package org.qtestapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import com.bumptech.glide.Glide
import org.qtestapp.R
import org.qtestapp.cache.GifCache
import org.qtestapp.rest.model.response.GifData
import java.io.File
import java.net.URI


class GifsRecyclerViewAdapter(private val context: Context,
                              private val layoutRes: Int,
                              private val gifCache: GifCache,
                              private var cacheAction: (id: String, url: String) -> Unit) :
        BaseRecyclerViewAdapter<GifsRecyclerViewAdapter.GifItemViewHolder, GifData>() {


    override fun onBindViewHolder(holder: GifItemViewHolder?, position: Int) {
        with(holder) {
            this?.let {

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