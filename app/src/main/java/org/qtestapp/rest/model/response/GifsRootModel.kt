package org.qtestapp.rest.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList


data class GifsRootModel (
        @SerializedName("data")
        @Expose
        var data: List<GifData> = ArrayList()
) {
    var gifUrls: List<String> = ArrayList()
        get() {
            return data.map {
                it -> it.images?.fixedHeightDownsampled?.url!!
            }
        }
}