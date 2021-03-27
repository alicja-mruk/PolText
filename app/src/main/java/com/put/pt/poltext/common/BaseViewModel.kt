package com.put.pt.poltext.common

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener

abstract class BaseViewModel(protected val onFailureListener: OnFailureListener) : ViewModel()