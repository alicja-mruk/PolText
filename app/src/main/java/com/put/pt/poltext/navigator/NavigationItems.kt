package com.put.pt.poltext.navigator

import com.put.pt.poltext.R

class NavigationItems {
    companion object {
        val data = arrayListOf(
            NavigationItemModel(R.drawable.ic_chat_private, "Private Chat"),
            NavigationItemModel(R.drawable.ic_chat_public, "Public Chat"),
            NavigationItemModel(R.drawable.ic_profile, "Profile"),
        )
    }
}