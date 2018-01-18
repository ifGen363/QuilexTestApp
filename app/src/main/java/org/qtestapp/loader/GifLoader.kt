package org.qtestapp.loader

import android.widget.ImageView
import com.bumptech.glide.Glide


class GifLoader {

    fun loadToView(view: ImageView, from: Any) {
        Glide
                .with(view.context)
                .load(from)
                .into(view)
    }
}
