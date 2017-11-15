package org.qtestapp.rest.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class FixedHeightDownsampled(
        @SerializedName("url")
        @Expose
        var url: String? = ""
)