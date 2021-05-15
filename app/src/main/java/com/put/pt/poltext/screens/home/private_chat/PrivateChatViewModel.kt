package com.put.pt.poltext.screens.home.private_chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.toObjects
import com.put.pt.poltext.model.User
import com.put.pt.poltext.repository.users.FirebaseUsersRepositoryImpl
import com.put.pt.poltext.screens.State
import java.util.ArrayList

class PrivateChatViewModel(private val userRepository: FirebaseUsersRepositoryImpl) : ViewModel() {
    val users = MutableLiveData<ArrayList<User>>()
    private val _state = MutableLiveData(State.EMPTY)
    val state = _state

    init {
        getUsers()
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
}