package org.qtestapp.extentions

import android.text.TextUtils
import android.widget.EditText


fun String.isNotEmpty(): Boolean = !TextUtils.isEmpty(this)

fun EditText.isNotEmpty(): Boolean = this.text.toString().isNotEmpty()
