package com.put.pt.poltext.screens.home.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.put.pt.poltext.common.SingleLiveEvent
import com.put.pt.poltext.data.firebase.common.auth

class SettingsViewModel: ViewModel() {
    private val _goToLoginScreen = SingleLiveEvent<Unit>()
    val goToLoginScreen: LiveData<Unit> = _goToLoginScreen

    fun logout(){
        auth.signOut()
        _goToLoginScreen.call()
    }
}