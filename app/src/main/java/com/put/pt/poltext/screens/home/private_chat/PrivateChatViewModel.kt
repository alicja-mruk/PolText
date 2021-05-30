package com.put.pt.poltext.screens.home.private_chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.put.pt.poltext.common.SingleLiveEvent
import com.put.pt.poltext.data.firebase.common.auth
import com.put.pt.poltext.model.PrivateChatMessage
import com.put.pt.poltext.model.User
import com.put.pt.poltext.repository.users.DatabaseConstants
import com.put.pt.poltext.repository.users.FirebaseUsersRepositoryImpl
import com.put.pt.poltext.screens.State
import java.text.SimpleDateFormat
import java.util.*


class PrivateChatViewModel(private val userRepository: FirebaseUsersRepositoryImpl) : ViewModel() {
    val users = MutableLiveData<ArrayList<User>>()
    val currentUser = MutableLiveData<User>()
    val messages = MutableLiveData<ArrayList<PrivateChatMessage>>()
    private val _notifyDataSetChanged = SingleLiveEvent<Unit>()
    val notifyDataSetChanged: LiveData<Unit> = _notifyDataSetChanged
    private val _state = MutableLiveData(State.EMPTY)
    val state = _state

    private val _chatItemState = MutableLiveData<ChatItemState>()
    val chatItemState = _chatItemState

    init {
        getCurrentUser()
        getUsers()
        listenToMessages()
    }

    private fun getCurrentUser() {
        if (auth.currentUser != null) {
            userRepository.getUser(auth.currentUser.uid).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    currentUser.postValue(task.result?.toObject<User>())
                }
            }
        }
    }

    fun getUsers() {
        _state.value = State.LOADING
        userRepository.getUsers().get().addOnSuccessListener { result ->
            for (document in result) {
                val _data = result?.toObjects<User>()
                val data = arrayListOf<User>()
                _data?.filterTo(data, { it.uid != auth.currentUser.uid })
                data.let {
                    users.postValue(it as ArrayList<User>?)
                }
                _state.value = State.SUCCESS
            }
        }
    }

    fun sendMessage(message: String, userToUid: String) {
        if (auth.currentUser == null) return

        _chatItemState.value = ChatItemState.SendMessageLoading
        val timestamp: String =
            SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault()).format(Date())
        userRepository.sendMessageToPrivateChannel(
            message,
            userToUid,
            auth.currentUser.uid,
            timestamp
        )
            .addOnSuccessListener {
                _chatItemState.value = ChatItemState.SendMessageSuccess
            }
    }

    fun listenToMessages() {
        _chatItemState.value = ChatItemState.Loading
        userRepository.getPrivateChannelMessages()
            .orderBy(DatabaseConstants.TIMESTAMP, Query.Direction.ASCENDING)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    _chatItemState.value = ChatItemState.Failure("Listen has failed")
                    return@addSnapshotListener
                }
                if (auth.currentUser == null) {
                    _chatItemState.value = ChatItemState.Failure("Current user is null")
                    return@addSnapshotListener
                }

                val data = ArrayList<PrivateChatMessage>()
                for (doc in value!!) {
                    data.add(doc.toObject(PrivateChatMessage::class.java))
                }
                messages.postValue(data)

                if (messages.value?.size == 0) {
                    _chatItemState.value = ChatItemState.Empty
                    return@addSnapshotListener
                }

                _chatItemState.value = ChatItemState.Success
                _notifyDataSetChanged.value = Unit
            }
    }

    sealed class ChatItemState {
        object Empty : ChatItemState()
        object Loading : ChatItemState()
        object SendMessageLoading : ChatItemState()
        object SendMessageSuccess : ChatItemState()
        object Success : ChatItemState()
        data class Failure(val message: String?) : ChatItemState()
    }
}