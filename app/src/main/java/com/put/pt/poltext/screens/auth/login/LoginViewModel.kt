package com.put.pt.poltext.screens.auth.login

import android.app.Application
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.OnFailureListener
import com.put.pt.poltext.R
import com.put.pt.poltext.common.AuthManager
import com.put.pt.poltext.common.BaseViewModel
import com.put.pt.poltext.common.CommonViewModel
import com.put.pt.poltext.common.SingleLiveEvent


class LoginViewModel(
    private val authManager: AuthManager,
    private val app: Application,
    private val commonViewModel: CommonViewModel,
    onFailureListener: OnFailureListener
) : BaseViewModel(onFailureListener) {

    private val _goToHomeScreen = SingleLiveEvent<Unit>()
    val goToHomeScreen: LiveData<Unit> = _goToHomeScreen
    private val _goToRegisterScreen = SingleLiveEvent<Unit>()
    val goToRegisterScreen: LiveData<Unit> = _goToRegisterScreen

    private val _showProgressBar = SingleLiveEvent<Unit>()
    val showProgressBar: LiveData<Unit> = _showProgressBar

    private val _hideProgressBar = SingleLiveEvent<Unit>()
    val hideProgressBar: LiveData<Unit> = _hideProgressBar

    fun onLoginClick(email: String, password: String) {
        _showProgressBar.call()
        if (validate(email, password)) {
            authManager.login(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    _hideProgressBar.call()
                    _goToHomeScreen.call()
                } else {
                    commonViewModel.setErrorMessage(it.exception?.message)
                    _hideProgressBar.call()
                }
            }
        } else {
            commonViewModel.setErrorMessage(app.getString(R.string.enter_name_and_password))
        }
    }

    private fun validate(email: String, password: String) =
        email.isNotEmpty() && password.isNotEmpty()

    fun onRegisterClick() {
        _goToRegisterScreen.call()
    }
}