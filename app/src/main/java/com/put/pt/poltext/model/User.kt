package com.put.pt.poltext.model

import java.io.Serializable

data class User(
    val username: String="",
    val email:String="",
    val photoUrl: String? = "",
    val uid: String = ""
): Serializable {
    constructor() : this ("","","","")
}