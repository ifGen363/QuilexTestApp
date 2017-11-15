package org.qtestapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import org.qtestapp.R


class GifsRecyclerViewAdapter(private val context: Context, private val layoutRes: Int) :
        BaseRecyclerViewAdapter<GifsRecyclerViewAdapter.GifItemViewHolder, String>() {


    override fun onBindViewHolder(holder: GifItemViewHolder?, position: Int) {
        with(holder) {
            this?.let {
                loadGif(context, data[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GifItemViewHolder =
            GifItemViewHolder(LayoutInflater.from(parent?.context).inflate(layoutRes, parent, false))


    class GifItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val gifImageView: ImageView = itemView.findViewById(R.id.gifImageView)

        fun loadGif(context: Context, url: String) {
            Glide
                    .with(context)
                    .load(url)
                    .into(gifImageView)
        }
    }
}