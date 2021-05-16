package com.put.pt.poltext.screens.home.private_chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.put.pt.poltext.data.firebase.common.auth
import com.put.pt.poltext.model.User
import com.put.pt.poltext.repository.users.FirebaseUsersRepositoryImpl
import com.put.pt.poltext.screens.State
import java.util.ArrayList

class PrivateChatViewModel(private val userRepository: FirebaseUsersRepositoryImpl) : ViewModel() {
    val users = MutableLiveData<ArrayList<User>>()
    val currentUser = MutableLiveData<User>()
    private val _state = MutableLiveData(State.EMPTY)
    val state = _state

    init {
        getCurrentUser()
        getUsers()
    }

    private fun getCurrentUser () {
        if (auth.currentUser != null) {
            userRepository.getUser(auth.currentUser.uid).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    currentUser.postValue(task.result?.toObject<User>())
                }
            }
        }
    }

    fun getUsers () {
        _state.value = State.LOADING
        userRepository.getUsers().get().addOnSuccessListener {  result ->
            for (document in result) {
              val data = result?.toObjects<User>()
                data?.let{
                    users.postValue(it as ArrayList<User>?)
                }
                _state.value = State.SUCCESS
            }
        }
    }

    fun sendMessage(text: String) {

    }
}