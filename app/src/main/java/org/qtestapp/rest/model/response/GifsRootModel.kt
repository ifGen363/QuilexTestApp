package org.qtestapp.rest.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList


data class GifsRootModel (
        @SerializedName("data")
        @Expose
        val gifsList: List<GifData> = ArrayList()
)