package com.put.pt.poltext.model

class PrivateChatMessage (val id : String, val text: String, val sendBy: String) {
    constructor() : this("", "", "", )
}