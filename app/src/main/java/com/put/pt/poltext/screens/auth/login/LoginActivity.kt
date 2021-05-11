package com.put.pt.poltext.screens.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.put.pt.poltext.databinding.ActivityLoginBinding
import com.put.pt.poltext.utils.setOnSingleClickListener
import com.put.pt.poltext.screens.BaseActivity
import com.put.pt.poltext.screens.auth.register.RegisterActivity
import com.put.pt.poltext.screens.home.HomeActivity
import com.put.pt.poltext.utils.hideProgressBar
import com.put.pt.poltext.utils.showProgressBar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent.setEventListener
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener

@ExperimentalCoroutinesApi
class LoginActivity : BaseActivity(), KeyboardVisibilityEventListener {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        loginViewModel = initViewModel()

        registerObserveListeners()

        setEventListener(this, this)
        registerOnClickListeners()
    }

    private fun registerObserveListeners(){
        loginViewModel.goToHomeScreen.observe(this, {
            hideProgressBar(binding.progressBar, binding.loginButton)
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
            finish()
        })

        loginViewModel.goToRegisterScreen.observe(this, {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        })

        loginViewModel.showProgressBar.observe(this,{
            showProgressBar(binding.progressBar, binding.loginButton)
        })

        loginViewModel.hideProgressBar.observe(this, {
            hideProgressBar(binding.progressBar, binding.loginButton)
        })
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
        binding.loginButton.setOnSingleClickListener {
            loginViewModel.onLoginClick(
                email = binding.emailEditText.text.toString(),
                password = binding.passwordEditText.text.toString()
            )
        }

        binding.moveToRegisterScreenBtn.setOnSingleClickListener {
            loginViewModel.onRegisterClick()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}