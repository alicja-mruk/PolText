package com.put.pt.poltext.screens.home.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.toObject
import com.put.pt.poltext.common.SingleLiveEvent
import com.put.pt.poltext.data.firebase.common.auth
import com.put.pt.poltext.model.User
import com.put.pt.poltext.repository.users.FirebaseUsersRepositoryImpl
import com.put.pt.poltext.screens.State

class ProfileViewModel(private val userRepository: FirebaseUsersRepositoryImpl) : ViewModel() {
    var user = MutableLiveData<User>()

    private val _moveToProfileScreen = SingleLiveEvent<Unit>()
    val moveToProfileScreen: LiveData<Unit> = _moveToProfileScreen

    private val _state = MutableLiveData(State.EMPTY)
    val state = _state

    private val _updateUserState = MutableLiveData<UIState>()
    val updateUserState = _updateUserState

    private var updateUserCondition = MutableList<Boolean>(3) {true}

    init {
        getUser()
    }

    private fun getUser() {
        if (auth.currentUser != null) {
            _state.value = State.LOADING
            userRepository.getUser(auth.currentUser.uid).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user.postValue(task.result?.toObject<User>())
                    _state.value = State.SUCCESS
                }
            }
        }
    }

    private fun uploadAndSetUserPhoto(localImage: Uri): Task<Unit> {
        return userRepository.uploadUserPhoto(localImage).onSuccessTask { downloadUri ->
            userRepository.updateUserPhoto(downloadUri).addOnSuccessListener {
                updateUserCondition[0] = true
            }
        }.addOnFailureListener {
            updateUserCondition[0] = false
            _updateUserState.postValue(UIState.Failure(it.message))
        }
    }

    private fun updateUsername(username: String) {
        userRepository.updateUsername(username).addOnSuccessListener {
            updateUserCondition[1] = true
        }.addOnFailureListener {
            updateUserCondition[1] = false
            _updateUserState.postValue(UIState.Failure(it.message))
        }
    }

    private fun updateEmail(email: String) {
        userRepository.updateUserEmail(email).addOnSuccessListener {
            updateUserCondition[2] = true
        }.addOnFailureListener {
            updateUserCondition[2] = false
            _updateUserState.postValue(UIState.Failure(it.message))
        }
    }

    fun updateUser(photoUrl: String, username: String, email: String) {
        _updateUserState.postValue(UIState.Loading)

        if (user.value?.photoUrl != photoUrl) {
            uploadAndSetUserPhoto(Uri.parse(photoUrl))
        }
        if (user.value?.username != username) {
            updateUsername(username)
        }
        if (user.value?.email != email) {
            updateEmail(email)
        }

        if(!updateUserCondition.contains(false)){
            _updateUserState.postValue(UIState.Success)
            return
        }
        _updateUserState.postValue(UIState.Failure("Error while updating user data"))
    }

    sealed class UIState {
        object Loading : UIState()
        object Success : UIState()
        data class Failure(val message: String?) : UIState()
    }
}