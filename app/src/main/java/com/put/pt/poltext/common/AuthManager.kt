package com.put.pt.poltext.common

import com.google.android.gms.tasks.Task

interface AuthManager {
    fun logout()
    fun login (email: String, password: String): Task<Unit>
}