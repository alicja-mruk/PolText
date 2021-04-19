package com.put.pt.poltext

import android.app.Application
import com.put.pt.poltext.data.firebase.common.FirebaseAuthManager
import com.put.pt.poltext.repository.users.FirebaseUsersRepositoryImpl

class App : Application() {

    val usersRepo by lazy { FirebaseUsersRepositoryImpl() }
    val authManager by lazy { FirebaseAuthManager() }

    override fun onCreate() {
        super.onCreate()
    }
}