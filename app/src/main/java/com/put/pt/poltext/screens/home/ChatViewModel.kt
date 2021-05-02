package com.put.pt.poltext.screens.home

import android.app.Application
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.ktx.toObject
import com.put.pt.poltext.R
import com.put.pt.poltext.common.BaseViewModel
import com.put.pt.poltext.common.CommonViewModel
import com.put.pt.poltext.model.PublicChatMessage
import com.put.pt.poltext.model.User
import com.put.pt.poltext.repository.users.FirebaseUsersRepositoryImpl
import java.text.SimpleDateFormat
import java.util.*

class ChatViewModel(
    private val commonViewModel: CommonViewModel,
    private val app: Application,
    private val userRepository: FirebaseUsersRepositoryImpl,
    onFailureListener: OnFailureListener
) : BaseViewModel(onFailureListener) {

    lateinit var uid: String
    lateinit var user: User
    val messages = MutableLiveData<ArrayList<PublicChatMessage>>()

    init {
        if (!this::uid.isInitialized) {
            this.uid = uid
            userRepository.getUser(uid).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // TODO: check if return correct value
                    this.user = task.result?.toObject<User>()!!
                    userRepository.getPublicChannelMessages().get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                messages.value?.add(document.toObject(PublicChatMessage::class.java))
                            }
                        }.addOnFailureListener(onFailureListener)

                } else {
                    // handle error. no user id
                }
            }
        }
    }

    fun onSendMessage(message: String) {
        if (message.isNotEmpty()) {
            val timestamp: String =
                SimpleDateFormat("ddMMyyyyhhmmss", Locale.getDefault()).format(Date())
            userRepository.sendMessageToPublicChannel(message, user, timestamp)
                .addOnSuccessListener {

                    // TODO success. Update UI. send event to ui.
                }.addOnFailureListener(onFailureListener)

        } else {
            commonViewModel.setErrorMessage(app.getString(R.string.send_message_error))
        }
    }

}
