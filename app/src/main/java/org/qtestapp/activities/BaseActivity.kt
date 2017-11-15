package org.qtestapp.activities

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.indeterminateProgressDialog
import org.qtestapp.R


abstract class BaseActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null

    abstract fun init()

    fun showProgressDialog(message: String = getString(R.string.loading), cancelable: Boolean = false, indeterminate: Boolean = true) {
        if (progressDialog == null && !this@BaseActivity.isFinishing) {
            runOnUiThread {
                try {
                    progressDialog = indeterminateProgressDialog(message) {
                        isIndeterminate = indeterminate
                        setCancelable(cancelable)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                progressDialog?.show()
            }
        }
    }

    fun cancelProgressDialog() {
        runOnUiThread {
            try {
                progressDialog?.cancel()
                progressDialog = null
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}