package org.qtestapp.adapters.holders

import android.support.annotation.IdRes
import android.view.View
import android.widget.ImageView
import org.qtestapp.R


class CachedGifItemViewHolder(itemView: View, @IdRes gifImageViewId: Int) : BaseGifViewHolder(itemView, gifImageViewId) {
    val deleteImageView: ImageView = itemView.findViewById(R.id.deleteImageView)
}