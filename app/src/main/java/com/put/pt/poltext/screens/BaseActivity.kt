package com.put.pt.poltext.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.put.pt.poltext.App
import com.put.pt.poltext.common.BaseViewModel
import com.put.pt.poltext.common.CommonViewModel
import com.put.pt.poltext.common.ViewModelFactory
import com.put.pt.poltext.utils.showToast

abstract class BaseActivity : AppCompatActivity() {

    lateinit var commonViewModel: CommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        commonViewModel = ViewModelProvider(this).get(CommonViewModel::class.java)
        commonViewModel.errorMessage.observe(this, {
            it?.let {
                showToast(it)
            }
        })
    }

    inline fun <reified T : BaseViewModel> initViewModel(): T =
        ViewModelProvider(
            this,
            ViewModelFactory(application as App, commonViewModel, commonViewModel)
        )
            .get(T::class.java)

    companion object {
        const val TAG = "BaseActivity"
    }
}