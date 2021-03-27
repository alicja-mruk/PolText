package com.put.pt.poltext.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnFailureListener
import com.put.pt.poltext.App
import com.put.pt.poltext.screens.auth.login.LoginViewModel
import com.put.pt.poltext.screens.auth.register.RegisterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val app: App,
    private val commonViewModel: CommonViewModel,
    private val onFailureListener: OnFailureListener
) : ViewModelProvider.Factory {

    @ExperimentalCoroutinesApi
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val usersRepos = app.usersRepo
        val authManager = app.authManager


        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(authManager, app, commonViewModel, onFailureListener) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(commonViewModel, app, usersRepos, onFailureListener) as T
        } else {
            error("Unknown View Model class $modelClass")
        }
    }
}