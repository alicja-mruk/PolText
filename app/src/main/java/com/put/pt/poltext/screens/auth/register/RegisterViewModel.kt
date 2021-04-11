package com.put.pt.poltext.screens.auth.register


import android.app.Application
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.put.pt.poltext.R
import com.put.pt.poltext.common.BaseViewModel
import com.put.pt.poltext.common.CommonViewModel
import com.put.pt.poltext.common.SingleLiveEvent
import com.put.pt.poltext.data.firebase.common.auth
import com.put.pt.poltext.model.User
import com.put.pt.poltext.repository.users.FirebaseUsersRepositoryImpl
import com.put.pt.poltext.screens.State
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class RegisterViewModel(
    private val commonViewModel: CommonViewModel,
    private val app: Application,
    private val userRepository: FirebaseUsersRepositoryImpl,
    onFailureListener: OnFailureListener
) : BaseViewModel(onFailureListener) {

    private val _goToUsernamePasswordScreen = SingleLiveEvent<Unit>()
    private val _goBackToEmailScreen = SingleLiveEvent<Unit>()
    val goToUsernamePasswordScreen: LiveData<Unit> = _goToUsernamePasswordScreen
    val goBackToEmailScreen: LiveData<Unit> = _goBackToEmailScreen

    private val _registerState =  MutableLiveData(State.EMPTY)
    val registerState = _registerState

    private var email: String? = null
    private var photoUrl: String? = null

    fun onEmailEntered(email: String, photoUrl: String = "") {
        if (email.isNotEmpty()) {
            this.email = email
            this.photoUrl = photoUrl
            userRepository.isUserExistsForEmail(email).addOnSuccessListener { exists ->
                if (!exists) {
                    _goToUsernamePasswordScreen.value = Unit
                } else {
                    commonViewModel.setErrorMessage(app.getString(R.string.email_exists))
                }
            }.addOnFailureListener(onFailureListener)
        } else {
            commonViewModel.setErrorMessage(app.getString(R.string.enter_email))
        }
    }

    fun onRegister(username: String, password: String) {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            val localEmail = email
            val localPhotoUrl = photoUrl

            _registerState.value = State.LOADING
            if (localEmail != null) {
                auth.createUserWithEmailAndPassword(localEmail, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            user?.let {
                                userRepository.createUser(
                                    makeUser(user.uid, username, localEmail),
                                    password
                                ).addOnSuccessListener {
                                    if(!localPhotoUrl.isNullOrEmpty()){
                                        uploadAndSetUserPhoto(Uri.parse(localPhotoUrl)).addOnSuccessListener {
                                            _registerState.value = State.SUCCESS
                                        }
                                    }
                                    _registerState.value = State.SUCCESS
                                }.addOnFailureListener(onFailureListener)
                            }
                        } else {
                            _registerState.value = State.ERROR
                            commonViewModel.setErrorMessage(app.getString(R.string.create_user_failure))
                        }
                    }

            } else {
                _registerState.value = State.ERROR
                commonViewModel.setErrorMessage(app.getString(R.string.enter_email))
                _goBackToEmailScreen.call()
            }

        } else {
            _registerState.value = State.ERROR
            commonViewModel.setErrorMessage(app.getString(R.string.enter_name_and_password))
        }
    }

    private fun uploadAndSetUserPhoto(localImage: Uri): Task<Unit> =
        userRepository.uploadUserPhoto(localImage).onSuccessTask { downloadUri ->
            userRepository.updateUserPhoto(downloadUri)
        }.addOnFailureListener(onFailureListener)


    private fun makeUser(uid: String, username: String, email: String): User {
        return User(uid = uid, username = username, email = email)
    }
}