package org.qtestapp.rest

import org.qtestapp.rest.model.response.GifsRootModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by ifgen on 15.11.17.
 */

interface RestClient {

    companion object {

        const val API_KEY = "VpQgoHGHFUmM4setbHbAs32Il3TEB3ZF"

        const val TRANDING_LIST = "/v1/gifs/trending"
        const val SEARCH = "/v1/gifs/search"
    }

    @GET(TRANDING_LIST)
    fun getGifsList(@Query("api_key") key: String = API_KEY): Call<GifsRootModel>

    @GET(SEARCH)
    fun getGifsList(@Query("api_key") key: String = API_KEY, @Query("q") query: String): Call<GifsRootModel>

}
