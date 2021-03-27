package com.put.pt.poltext.model

import android.net.Uri
import com.google.firebase.database.Exclude

data class User(
    val username: String="",
    val email:String="",
    val photoUrl: String = "",
    @get:Exclude val uid: String = ""
)