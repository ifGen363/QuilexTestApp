package org.qtestapp.rest.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class GifData(
        @SerializedName("id")
        @Expose
        val id: String,
        @SerializedName("images")
        @Expose
        val images: Images? = null
) {
    var url: String? = ""
        get() = this.images?.fixedHeightDownsampled?.url
}