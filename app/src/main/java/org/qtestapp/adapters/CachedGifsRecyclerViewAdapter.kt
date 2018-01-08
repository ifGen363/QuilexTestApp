package org.qtestapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import org.qtestapp.R
import org.qtestapp.cache.GifCache
import java.io.File
import java.net.URI

/**
 * Created by MartulEI on 08.01.2018.
 */
class CachedGifsRecyclerViewAdapter(
        val context: Context,
        private val layoutRes: Int,
        private val gifCache: GifCache) : BaseRecyclerViewAdapter<CachedGifsRecyclerViewAdapter.CachedGifItemViewHolder, String>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CachedGifItemViewHolder =
            CachedGifItemViewHolder(LayoutInflater.from(parent?.context).inflate(layoutRes, parent, false))


    override fun onBindViewHolder(holder: CachedGifItemViewHolder?, position: Int) {
        with(holder) {
            this?.let {
                deleteButton.setOnClickListener {
                    val pos: Int = holder?.adapterPosition!!
                    gifCache.remove(data[pos])
                    data.removeAt(pos)
                    notifyItemRemoved(pos)
                }

                gifCache.get(data[position])?.file?.toURI()?.let { uri -> loadGifFromCache(context, uri) }
            }
        }
    }


    class CachedGifItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val gifImageView: ImageView = itemView.findViewById(R.id.gifImageView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        fun loadGifFromCache(context: Context, uri: URI) {
            Glide
                    .with(context)
                    .load(File(uri))
                    .into(gifImageView)
        }
    }
}