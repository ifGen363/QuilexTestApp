package org.qtestapp.rest.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class GifData(
        @SerializedName("id")
        @Expose
        var id: String = "",
        @SerializedName("images")
        @Expose
        var images: Images? = null
) {
    var url: String? = ""
        get() = this.images?.fixedHeightDownsampled?.url
}