package com.put.pt.poltext.model


class PublicChatMessage (
    val uid: String,
    val message:String,
    val timestamp:String,
){
    constructor() : this("", "", "")
}