package com.put.pt.poltext.model

import com.google.firebase.firestore.Exclude

data class PublicChatMessage (
    val user: User,
    val message:String,
    val timestamp:String,
    @get:Exclude val id: String = "",
)