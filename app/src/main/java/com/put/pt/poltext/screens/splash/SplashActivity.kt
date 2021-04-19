package com.put.pt.poltext.screens.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.put.pt.poltext.R
import com.put.pt.poltext.screens.auth.login.LoginActivity
import com.put.pt.poltext.screens.home.HomeActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.SplashScreenTheme)

        val user = FirebaseAuth.getInstance().currentUser

        routeToAppropriatePage(user)
        finish()
    }


    private fun routeToAppropriatePage(user: FirebaseUser?) {
        when (user) {
            null -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            else ->  startActivity(Intent(this, HomeActivity::class.java))

        }
    }
}