package org.qtestapp.adapters.holders

import android.support.annotation.IdRes
import android.view.View
import android.widget.CheckBox
import org.qtestapp.R


class GifItemViewHolder(itemView: View, @IdRes gifImageViewId: Int) : BaseGifViewHolder(itemView, gifImageViewId) {
    val isCachedCheckBox: CheckBox = itemView.findViewById(R.id.isCachedCheckBox)
}