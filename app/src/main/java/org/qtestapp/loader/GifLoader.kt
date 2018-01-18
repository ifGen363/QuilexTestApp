package org.qtestapp.loader

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File


class GifLoader {

    fun loadToView(view: ImageView, from: String) {
        Glide
                .with(view.context)
                .load(from)
                .into(view)
    }

    fun loadToView(view: ImageView, from: File) {
        Glide
                .with(view.context)
                .load(from)
                .into(view)
    }
}
