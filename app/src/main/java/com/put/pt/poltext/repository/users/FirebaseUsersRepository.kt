package com.put.pt.poltext.repository.users

import android.net.Uri
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.put.pt.poltext.data.firebase.common.database
import com.put.pt.poltext.data.firebase.common.liveData
import com.put.pt.poltext.model.User

interface FirebaseUsersRepository {
    suspend fun setUserImage(uid: String, downloadUri: Uri): Task<Unit>
    suspend fun uploadUserPhoto (uid: String, photoUrl: Uri) : Task<Uri>
    suspend fun getImages(uid: String): LiveData<List<String>>
    suspend fun createUser(user: User, password: String) : Task<Unit>
    suspend fun getUser(): LiveData<User>
    suspend fun getUser(uid: String): LiveData<User>
    suspend fun getUsers(): LiveData<List<User>>
    fun currentUid(): String
}