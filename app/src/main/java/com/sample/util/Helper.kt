package com.sample.util

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun maskString(strText: String?, start: Int, end: Int, maskChar: Char): String? {
    var start = start
    var end = end
    if (strText == null || strText == "") return ""
    if (start < 0) start = 0
    if (end > strText.length) end = strText.length
    if (start > end) throw java.lang.Exception("End index cannot be greater than start index")
    val maskLength = end - start
    if (maskLength == 0) return strText
    val sbMaskString = StringBuilder(maskLength)
    for (i in 0 until maskLength) {
        sbMaskString.append(maskChar)
    }
    return (strText.substring(0, start)
            + sbMaskString.toString()
            + strText.substring(start + maskLength))
}

fun convertDateFormat(strDate: String): String {
    var dateFormat = ""
    try {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        val date = sdf.parse(strDate)
        sdf.applyPattern("dd MMM yyyy, hh:mm:ss a")
        dateFormat = sdf.format(date!!)
        return dateFormat
    } catch (ex: Exception) {
        ex.printStackTrace()
        return ""
    }
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
}

fun View.snackbar(message: String, showTime: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, showTime).also { snackBar ->
        snackBar.setAction("ok") {
            snackBar.dismiss();
        }
    }.show()
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
