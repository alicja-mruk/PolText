package com.put.pt.poltext.model


class PublicChatMessage (
    val id: String,
    val uid: String,
    val message:String,
    val timestamp:String,
){
    constructor() : this("","", "", "")
}