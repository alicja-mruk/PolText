package com.put.pt.poltext.screens.home

import android.app.Application
import com.google.android.gms.tasks.OnFailureListener
import com.put.pt.poltext.common.BaseViewModel
import com.put.pt.poltext.common.CommonViewModel
import com.put.pt.poltext.repository.users.FirebaseUsersRepositoryImpl

class ChatViewModel(
    private val commonViewModel: CommonViewModel,
    private val app: Application,
    private val userRepository: FirebaseUsersRepositoryImpl,
    onFailureListener: OnFailureListener
) : BaseViewModel(onFailureListener) {

    fun onSendMessage (message: String){
//        TODO: send message to public channel implementation
    }

}
