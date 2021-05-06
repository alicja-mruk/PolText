package com.put.pt.poltext.model

import com.google.firebase.firestore.Exclude

data class User(
    val username: String="",
    val email:String="",
    val photoUrl: String? = "",
    val uid: String = ""
)