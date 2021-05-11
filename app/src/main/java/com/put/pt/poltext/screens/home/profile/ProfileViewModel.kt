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

    fun updateUser(username: String) {
        _updateUserState.postValue(UIState.Loading)
        if (user.value?.username != username) {
            userRepository.updateUsername(username).addOnSuccessListener {
                _updateUserState.postValue(UIState.Success("Successfully updated username!"))
                _moveToProfileScreen.call()
            }.addOnFailureListener {
                _updateUserState.postValue(UIState.Failure(it.message))
            }
            return
        }
        _updateUserState.postValue(UIState.Failure("Error while updating user data"))
    }

    sealed class UIState {
        object Loading : UIState()
        data class Success(val message: String?) : UIState()
        data class Failure(val message: String?) : UIState()
    }
}