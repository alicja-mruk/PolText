package com.put.pt.poltext.model

class PrivateChatMessage (val id : String, val text: String, val fromId:String, val toId:String, val timestamp: String) {
    constructor() : this("", "", "", "", "")
}