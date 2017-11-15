package org.qtestapp.rest.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Images(
        @SerializedName("fixed_height_downsampled")
        @Expose
        var fixedHeightDownsampled: FixedHeightDownsampled? = null
)