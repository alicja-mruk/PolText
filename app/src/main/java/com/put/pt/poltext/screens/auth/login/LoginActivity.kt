package com.put.pt.poltext.screens.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.put.pt.poltext.databinding.ActivityLoginBinding
import com.put.pt.poltext.screens.BaseActivity
import com.put.pt.poltext.screens.auth.register.RegisterActivity
import com.put.pt.poltext.screens.home.HomeActivity
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent.setEventListener
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener


class LoginActivity : BaseActivity(), KeyboardVisibilityEventListener {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        //TODO: bind inputs with button so when inputs are empty disbale button

        loginViewModel = initViewModel()

        loginViewModel.goToHomeScreen.observe(this, {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        })

        loginViewModel.goToRegisterScreen.observe(this, {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        })

        setEventListener(this, this)
        registerOnClickListeners()
    }

    override fun onVisibilityChanged(isOpen: Boolean) {
        if (isOpen) {
            binding.scrollView.scrollTo(0, binding.scrollView.bottom)
            binding.moveToRegisterScreenBtn.visibility = View.GONE
        } else {
            binding.scrollView.scrollTo(0, binding.scrollView.top)
            binding.moveToRegisterScreenBtn.visibility = View.VISIBLE
        }
    }

    private fun registerOnClickListeners() {
        binding.loginButton.setOnClickListener {
            loginViewModel.onLoginClick(
                email = binding.emailEditText.toString(),
                password = binding.passwordEditText.toString()
            )
        }

        binding.moveToRegisterScreenBtn.setOnClickListener {
            loginViewModel.onRegisterClick()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}