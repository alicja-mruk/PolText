package com.put.pt.poltext.screens.home.public_chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.put.pt.poltext.common.SingleLiveEvent
import com.put.pt.poltext.data.firebase.common.auth
import com.put.pt.poltext.model.PublicChatMessage
import com.put.pt.poltext.model.PublicChatMessageParsed
import com.put.pt.poltext.model.User
import com.put.pt.poltext.repository.users.DatabaseConstants
import com.put.pt.poltext.repository.users.FirebaseUsersRepositoryImpl
import com.put.pt.poltext.screens.State
import java.text.SimpleDateFormat
import java.util.*

class PublicChatViewModel(private val userRepository: FirebaseUsersRepositoryImpl) : ViewModel() {
    var user = MutableLiveData<User>()
    val messages = MutableLiveData<ArrayList<PublicChatMessageParsed>>()

    private val _resetInput = SingleLiveEvent<Unit>()
    val resetInput: LiveData<Unit> = _resetInput
    private val _notifyDataSetChanged = SingleLiveEvent<Unit>()
    val notifyDataSetChanged: LiveData<Unit> = _notifyDataSetChanged
    private val _moveToLoginScreen = SingleLiveEvent<Unit>()
    val moveToLoginScreen: LiveData<Unit> = _moveToLoginScreen

    private val _state = MutableLiveData(State.EMPTY)
    val state = _state

    init {
        if (auth.currentUser != null) {
            _state.value = State.LOADING
            userRepository.getUser(auth.currentUser.uid).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user.postValue(task.result?.toObject<User>())
                    setPublicMessagesListener()
                }
            }
        } else {
            _moveToLoginScreen.call()
        }
    }

    private fun setPublicMessagesListener() {
        userRepository.getPublicChannelMessages()
            .orderBy(DatabaseConstants.TIMESTAMP, Query.Direction.ASCENDING)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                val data = ArrayList<PublicChatMessage>()
                for (doc in value!!) {
                    data.add(doc.toObject(PublicChatMessage::class.java))
                }

                val parsedData = ArrayList<PublicChatMessageParsed>()

                data.forEach { item ->
                    userRepository.getUser(item.uid).get().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val fetchedUser = task.result?.toObject<User>()
                            parsedData.add(
                                PublicChatMessageParsed(
                                    fetchedUser!!,
                                    item.message,
                                    item.timestamp
                                )
                            )
                            if (parsedData.size == data.size) {
                                messages.postValue(parsedData)
                                _state.value = State.SUCCESS
                                _notifyDataSetChanged.value = Unit
                            }
                        }
                    }
                }

            }
    }

    fun refetchMessages() {
        messages.value?.clear()
        setPublicMessagesListener()

    }

    fun onSendMessage(message: String) {
        if (message.isNotEmpty()) {
            val timestamp: String =
                SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault()).format(Date())
            user.value?.let {
                userRepository.sendMessageToPublicChannel(message, it.uid, timestamp)
                    .addOnSuccessListener {
                        _resetInput.value = Unit
                    }
            }
        }
    }

    companion object {
        const val TAG = "ChatViewModel"
    }
}
