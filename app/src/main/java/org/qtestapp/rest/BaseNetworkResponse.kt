package org.qtestapp.rest

import android.app.Activity
import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.qtestapp.activities.BaseActivity
import org.qtestapp.extentions.showMessageDialog
import retrofit2.Response


abstract class BaseNetworkResponse<T>(override val context: Context) : RestFactory.CallbackNetworkResponse<T> {

    override fun onRequestStart() {
        (context as? BaseActivity)?.showProgressDialog()
    }

    override fun onUnsuccessfulResult(response: Response<T>) {
        try {
            val jsonObject = Gson().fromJson(response.errorBody()?.string(), JsonObject::class.java)
            for ((_, value) in jsonObject.entrySet()) {
                onFailure(value.asString)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.let { onFailure(it) }
        }
    }

    override fun onFailure(message: String) {
        (context as? Activity)?.showMessageDialog(message)
    }

    override fun onRequestFinish() {
        (context as? BaseActivity)?.cancelProgressDialog()
    }
}


abstract class SwipeToRefreshNetworkResponse<T>(
        override val context: Context,
        private val progressView: SwipeRefreshLayout?) : BaseNetworkResponse<T>(context) {

    override fun onRequestStart() {
        progressView?.isRefreshing = true
    }

    override fun onRequestFinish() {
        progressView?.isRefreshing = false
    }
}