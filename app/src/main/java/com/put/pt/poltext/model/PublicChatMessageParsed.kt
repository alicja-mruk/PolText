package com.put.pt.poltext.model


class PublicChatMessageParsed (
    val user: User,
    val message:String,
    val timestamp:String,
){
    constructor() : this(User("","","",""), "", "")
}