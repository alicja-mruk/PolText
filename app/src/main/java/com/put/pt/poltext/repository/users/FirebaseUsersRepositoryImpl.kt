package com.put.pt.poltext.repository.users

import android.net.Uri
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.put.pt.poltext.common.FirebaseLiveData
import com.put.pt.poltext.common.map
import com.put.pt.poltext.common.task
import com.put.pt.poltext.common.toUnit
import com.put.pt.poltext.data.firebase.common.auth
import com.put.pt.poltext.data.firebase.common.database
import com.put.pt.poltext.data.firebase.common.liveData
import com.put.pt.poltext.data.firebase.common.storage
import com.put.pt.poltext.model.User

class FirebaseUsersRepositoryImpl : FirebaseUsersRepository {

    override suspend fun setUserImage(uid: String, downloadUri: Uri): Task<Unit> =
        database.child("images").child(uid).push()
            .setValue(downloadUri).toUnit()


    override suspend fun uploadUserPhoto(uid: String, photoUrl: Uri): Task<Uri> =
        task { taskSource ->
            val ref = storage.child("users").child(uid).child("images")
                .child(photoUrl.lastPathSegment!!)
            ref.putFile(photoUrl).addOnCompleteListener {
                if (it.isSuccessful) {
                    ref.downloadUrl.addOnCompleteListener {
                        taskSource.setResult(it.result)
                    }
                } else {
                    taskSource.setException(it.exception!!)
                }
            }
        }

    override suspend fun getImages(uid: String): LiveData<List<String>> =
        FirebaseLiveData(database.child("images").child(uid)).map {
            it.children.map { it.getValue(String::class.java)!! }
        }

    override suspend fun createUser(user: User, password: String): Task<Unit> =
        auth.createUserWithEmailAndPassword(user.email, password).onSuccessTask {
            database.child("users").child(it!!.user.uid).setValue(user)
        }.toUnit()

    override fun currentUid(): String  = FirebaseAuth.getInstance().currentUser!!.uid

    override suspend fun getUser(): LiveData<User> = getUser(currentUid())

    override suspend fun getUser(uid: String): LiveData<User> =
        database.child("users").child(uid).liveData().map {
            it.asUser()!!
        }


    override suspend fun getUsers(): LiveData<List<User>> =
        database.child("users").liveData().map {
            it.children.map { it.asUser()!! }

        }

    private fun DataSnapshot.asUser(): User? = getValue(User::class.java)?.copy(uid = key!!)
}