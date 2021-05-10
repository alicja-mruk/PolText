package com.put.pt.poltext.screens.auth.register


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.put.pt.poltext.R
import com.put.pt.poltext.databinding.ActivityRegisterBinding
import com.put.pt.poltext.screens.BaseActivity
import com.put.pt.poltext.screens.State
import com.put.pt.poltext.screens.auth.login.LoginActivity
import com.put.pt.poltext.screens.home.HomeActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class RegisterActivity : BaseActivity(),
    RegisterEmailPhoto.Listener,
    RegisterUsernamePassword.Listener {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        registerViewModel = initViewModel()

        registerObserveListeners()
    }


    private fun registerObserveListeners() {
        registerViewModel.goToUsernamePasswordScreen.observe(this, {
            it?.let {
                if (navHostFragment.childFragmentManager.fragments[0] is RegisterEmailPhoto) {
                    navHostFragment.navController.navigate(R.id.action_registerEmailPhoto_to_registerUsernamePassword)
                }
            }
        })

        registerViewModel.goBackToEmailScreen.observe(this, {
            it?.let {
                navHostFragment.navController.popBackStack()
            }
        })

        registerViewModel.registerState.observe(this, {
            when (it) {
                State.SUCCESS -> {
                    hideProgressBar()
                    startHomeActivity()
                }
                State.LOADING -> showProgressBar()
                State.ERROR -> hideProgressBar()
                else -> Unit
            }
        })
    }

    override fun onNext(email: String, photoUrl: String) {
        registerViewModel.onEmailEntered(email, photoUrl)
    }

    override fun onRegister(username: String, password: String) {
        registerViewModel.onRegister(username, password)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showProgressBar() {
        val fragment = navHostFragment.childFragmentManager.fragments[0]
        if (fragment is RegisterUsernamePassword) {
            fragment.showProgressBar()
        }
    }

    private fun hideProgressBar() {
        val fragment = navHostFragment.childFragmentManager.fragments[0]
        if (fragment is RegisterUsernamePassword) {
            fragment.hideProgressBar()
        }
    }

    private fun startHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        val f = navHostFragment.childFragmentManager.fragments[0]
        when (f) {
            is RegisterEmailPhoto -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
//          TODO: find the way to overcome 'LifecycleOwner is attempting to register while current state is RESUMED. LifecycleOwners must call register before they are STARTED.'
//            is RegisterUsernamePassword -> {
//               navHostFragment.navController.navigate(R.id.action_registerUsernamePassword_to_registerEmailPhoto)
//            }
        }
    }

}