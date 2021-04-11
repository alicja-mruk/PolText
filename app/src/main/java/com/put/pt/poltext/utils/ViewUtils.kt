package com.put.pt.poltext.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast


fun Context.showToast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    text?.let { Toast.makeText(this, it, duration).show() }
}

tailrec fun Context.activity(): Activity? = when (this) {
    is Activity -> this
    else -> (this as? ContextWrapper)?.baseContext?.activity()
}

fun showProgressBar(progressBar: ProgressBar, button: Button) {
    progressBar.visibility = View.VISIBLE
    button.visibility = View.INVISIBLE
}

fun hideProgressBar(progressBar: ProgressBar, button: Button) {
    progressBar.visibility = View.GONE
    button.visibility = View.VISIBLE
}