package com.put.pt.poltext.data.firebase.common

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


val auth: FirebaseAuth = FirebaseAuth.getInstance()
val storage: StorageReference = FirebaseStorage.getInstance().reference
