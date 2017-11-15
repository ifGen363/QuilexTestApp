package org.qtestapp.extentions

import android.app.Activity
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import org.qtestapp.R
import org.qtestapp.rest.RestClient
import org.qtestapp.rest.RestFactory
import retrofit2.Call


fun Activity.getClient(): RestClient = RestFactory.instance.client

fun <T> Activity.enqueue(call: Call<T>, result: RestFactory.CallbackNetworkResponse<T>) {
    if (isOnline()) {
        RestFactory.instance.enqueue(call, result)
    } else {
        alert(getString(R.string.no_internet_connection)) {
            positiveButton(getString(R.string.retry)) { enqueue(call, result) }
            negativeButton(getString(R.string.cancel)) { }
        }.show()
    }
}

fun Activity.showMessageDialog(message: String, title: String? = null, action: () -> Unit = {}) {
    alert(message, title) {
        yesButton { action() }
    }.show()
}
