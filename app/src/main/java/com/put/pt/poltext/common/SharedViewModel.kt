package com.put.pt.poltext.common

import android.app.Application
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.ktx.toObject
import com.put.pt.poltext.R
import com.put.pt.poltext.model.User
import com.put.pt.poltext.repository.users.FirebaseUsersRepositoryImpl

class SharedViewModel(
    private val commonViewModel: CommonViewModel,
    private val app: Application,
    private val userRepository: FirebaseUsersRepositoryImpl,
    onFailureListener: OnFailureListener
) : BaseViewModel(onFailureListener) {

    fun getUser (): User? {
        var user: User? = null
        userRepository.getUser(userRepository.currentUid()).get().addOnSuccessListener { documentSnapshot ->
            user = documentSnapshot.toObject<User>()
        }
        return user
    }

    fun getUsers (): ArrayList<User?> {
        val list = ArrayList<User?>()
        userRepository.getUsers().get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    val taskItem: User = document.toObject(User::class.java)
                    list.add(taskItem);
                }
            }
            else {
                commonViewModel.setErrorMessage(app.getString(R.string.fetch_users_failure))
            }
        }
        return list
    }
}