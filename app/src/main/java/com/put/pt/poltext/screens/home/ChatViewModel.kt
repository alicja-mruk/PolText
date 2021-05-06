package com.put.pt.poltext.screens.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.toObject
import com.put.pt.poltext.data.firebase.common.auth
import com.put.pt.poltext.model.PublicChatMessage
import com.put.pt.poltext.model.User
import com.put.pt.poltext.repository.users.FirebaseUsersRepositoryImpl
import java.text.SimpleDateFormat
import java.util.*

class ChatViewModel(private val userRepository: FirebaseUsersRepositoryImpl): ViewModel(){
    lateinit var user: User
    val messages = MutableLiveData<ArrayList<PublicChatMessage>>()

    init {
//        auth.signOut()
            userRepository.getUser(auth.currentUser.uid).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // TODO: check if return correct value
                    this.user = task.result?.toObject<User>()!!
                    userRepository.getPublicChannelMessages().get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                messages.value?.add(document.toObject(PublicChatMessage::class.java))
                            }
                        }
                }
            }
    }

    fun onSendMessage(message: String) {
        if (message.isNotEmpty()) {
            val timestamp: String = SimpleDateFormat("ddMMyyyyhhmmss", Locale.getDefault()).format(Date())
            userRepository.sendMessageToPublicChannel(message, user, timestamp)
                .addOnSuccessListener {

                    // TODO success. Update UI. send event to ui.
                }
                //.addOnFailureListener(onFailureListener)
        }
    }

}
