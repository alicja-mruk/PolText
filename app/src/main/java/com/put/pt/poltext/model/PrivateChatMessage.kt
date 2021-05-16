package com.put.pt.poltext.model

class PrivateChatMessage (val id : String, val message: String, val userFrom: String, val userTo:String ) {
    constructor() : this("", "", "", "")
}