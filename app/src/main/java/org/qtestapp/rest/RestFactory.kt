package org.qtestapp.rest


import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.qtestapp.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RestFactory private constructor() {

    private object Holder {
        val INSTANCE = RestFactory()
    }

    companion object {
        val instance: RestFactory by lazy { Holder.INSTANCE }
    }

    val client: RestClient
        get() {
            val retrofit = getRetrofit(okHttpClient)
            return retrofit.create(RestClient::class.java)
        }

    private val okHttpClient: OkHttpClient
        get() {
            val defaultHttpClient = OkHttpClient.Builder()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            defaultHttpClient.addInterceptor(interceptor)

            return defaultHttpClient.build()
        }


    private fun getRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }




    fun <T> enqueue(call: Call<T>, result: CallbackNetworkResponse<T>) {

        result.onRequestStart()
        call.enqueue(object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {
                result.onRequestFinish()

                if (response.isSuccessful) {
                    response.body()?.let { result.onResult(it) }
                } else {
                    result.onUnsuccessfulResult(response)
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                t.printStackTrace()
                result.onRequestFinish()
                t.message?.let { result.onFailure(it) }
            }
        })
    }


    interface CallbackNetworkResponse<T> {
        val context: Context
        fun onRequestStart()
        fun onResult(data: T)
        fun onUnsuccessfulResult(response: Response<T>)
        fun onFailure(message: String)
        fun onRequestFinish()
    }
}