package com.put.pt.poltext.repository.users

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.put.pt.poltext.model.User

interface FirebaseUsersRepository {
    fun setUserImage(uid: String, downloadUri: Uri): Task<Unit>
    fun uploadUserPhoto(localImage: Uri): Task<Uri>
    fun updateUserPhoto(downloadUrl: Uri?): Task<Unit>
    fun updateUsername(username: String): Task<Unit>
    fun updateUserEmail(email: String): Task<Unit>
    fun createUser(user: User, password: String) : Task<Unit>
    fun getUser(uid: String): DocumentReference
    fun getUsers():  CollectionReference
    fun currentUid(): String
    fun isUserExistsForEmail(email: String): Task<Boolean>
    fun sendMessageToPublicChannel(message: String, userId: String, timestamp: String): Task<Unit>
    fun getPublicChannelMessages(): CollectionReference
    fun sendMessageToPrivateChannel(message: String, userToUid: String, userFromUid: String): Task<Unit>
    fun getPrivateChannelMessages(): CollectionReference
}