package com.put.pt.poltext.screens.auth.register


import android.app.Application
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.OnFailureListener
import com.put.pt.poltext.R
import com.put.pt.poltext.common.BaseViewModel
import com.put.pt.poltext.common.CommonViewModel
import com.put.pt.poltext.common.SingleLiveEvent
import com.put.pt.poltext.model.User
import com.put.pt.poltext.repository.users.FirebaseUsersRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class RegisterViewModel(
    private val commonViewModel: CommonViewModel,
    private val app: Application,
    private val repositoryFirebase: FirebaseUsersRepositoryImpl,
    onFailureListener: OnFailureListener
) : BaseViewModel(onFailureListener) {

    private val _goToHomeScreen = SingleLiveEvent<Unit>()
    val goToHomeScreen: LiveData<Unit> = _goToHomeScreen
    private val _goToRegisterScreen = SingleLiveEvent<Unit>()
    val goToRegisterScreen: LiveData<Unit> = _goToRegisterScreen


    suspend fun register(username: String, email: String, photoUrl: String, password: String) {
        if (username.isNotEmpty() && email.isNotEmpty() && photoUrl.isNotEmpty() && password.isNotEmpty()) {
            repositoryFirebase.createUser(makeUser(username, photoUrl, email), password)
                .addOnSuccessListener {
                    _goToHomeScreen.call()
                }.addOnFailureListener(onFailureListener)
        } else {
            commonViewModel.setErrorMessage(app.getString(R.string.enter_name_and_password))
        }
    }

    fun onRegisterClick() {
        _goToRegisterScreen.call()
    }


    private fun makeUser(username: String, photoUrl: String, email: String): User {
        return User(username = username, email = email, photoUrl = photoUrl)
    }
}