package com.put.pt.poltext.utils

import android.view.View
import com.put.pt.poltext.helper.OnSingleClickListener

fun View.setOnSingleClickListener(l: View.OnClickListener) {
    setOnClickListener(OnSingleClickListener(l))
}

fun View.setOnSingleClickListener(l: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(l))
}