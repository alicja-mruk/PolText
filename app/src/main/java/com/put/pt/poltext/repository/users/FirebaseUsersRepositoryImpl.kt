package com.put.pt.poltext.repository.users

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.put.pt.poltext.common.task
import com.put.pt.poltext.common.toUnit
import com.put.pt.poltext.data.firebase.common.auth
import com.put.pt.poltext.data.firebase.common.storage
import com.put.pt.poltext.model.User


class FirebaseUsersRepositoryImpl : FirebaseUsersRepository {
    val database: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun setUserImage(uid: String, downloadUri: Uri): Task<Unit> =
        database.collection(DatabaseConstants.IMAGES).document(uid).set(downloadUri).toUnit()

    override fun uploadUserPhoto(localImage: Uri): Task<Uri> =
        task { taskSource ->
            val ref =
                storage.child("${DatabaseConstants.USERS}/${currentUid()}/${DatabaseConstants.PHOTO}")
            ref.putFile(localImage).addOnSuccessListener {
                ref.downloadUrl.addOnCompleteListener {
                    taskSource.setResult(it.result!!)
                }
            }
        }

    override fun updateUserPhoto(downloadUrl: Uri?): Task<Unit> =
        database.collection(DatabaseConstants.USERS).document(currentUid())
            .update(DatabaseConstants.PHOTO_URL, downloadUrl.toString()).toUnit()


    override fun createUser(user: User, password: String): Task<Unit> {
        val _user = hashMapOf(
            DatabaseConstants.USERNAME to user.username,
            DatabaseConstants.EMAIL to user.email,
            DatabaseConstants.UID to user.uid,
            DatabaseConstants.PHOTO_URL to user.photoUrl
        )

        return database.collection(DatabaseConstants.USERS).document(user.uid).set(_user).toUnit()
    }


    override fun currentUid(): String = auth.currentUser!!.uid

    override fun getUser(uid: String): DocumentReference = database.collection(DatabaseConstants.USERS).document(uid)

    override fun getUsers(): CollectionReference = database.collection(DatabaseConstants.USERS)

    override fun isUserExistsForEmail(email: String): Task<Boolean> =
        auth.fetchSignInMethodsForEmail(email).onSuccessTask {
            val signInMethods = it?.signInMethods ?: emptyList<String>()
            Tasks.forResult(signInMethods.isNotEmpty())
        }

    override fun sendMessageToPublicChannel(message: String, user: User, timestamp: String): Task<Unit>  {
        val _message = hashMapOf(
            DatabaseConstants.USER to user,
            DatabaseConstants.MESSAGE to message,
            DatabaseConstants.TIMESTAMP to timestamp
        )
        return database.collection(DatabaseConstants.PUBLIC_MESSAGES).document(timestamp).set(_message).toUnit()
    }

    override fun getPublicChannelMessages(): CollectionReference  = database.collection(DatabaseConstants.PUBLIC_MESSAGES)
}