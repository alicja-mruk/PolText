package com.put.pt.poltext.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.put.pt.poltext.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import com.put.pt.poltext.ui.auth.AuthActivity
import com.put.pt.poltext.ui.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.SplashScreenTheme)

        val user = FirebaseAuth.getInstance().currentUser

        val settings = firestoreSettings {
            isPersistenceEnabled = false
        }

        // This can only be executed once. Do not route to this activity more than once
        Firebase.firestore.firestoreSettings = settings


        routeToAppropriatePage(user)
        finish()
    }

    private fun routeToAppropriatePage(user: FirebaseUser?) {
        when (user) {
            null -> startActivity(Intent(this, AuthActivity::class.java))
            else -> {
                startActivity(Intent(this, HomeActivity::class.java))
            }
        }
    }
}