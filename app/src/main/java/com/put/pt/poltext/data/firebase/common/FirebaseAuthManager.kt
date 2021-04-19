package com.put.pt.poltext.data.firebase.common

import com.google.android.gms.tasks.Task
import com.put.pt.poltext.common.AuthManager
import com.put.pt.poltext.common.toUnit

class FirebaseAuthManager : AuthManager {

    override fun login(email: String, password: String): Task<Unit> =
        auth.signInWithEmailAndPassword(email, password).toUnit()

    override fun logout() {
        auth.signOut()
    }
}