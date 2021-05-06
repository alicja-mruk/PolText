package com.put.pt.poltext.screens.home


import android.os.Bundle
import androidx.navigation.NavController

import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.put.pt.poltext.R
import com.put.pt.poltext.databinding.ActivityHomeBinding
import com.put.pt.poltext.screens.BaseActivity
import com.put.pt.poltext.screens.home.public_chat.ChatPublicFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : BaseActivity(), ChatPublicFragment.Listener {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private val chatViewModel by viewModel<ChatViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        navController = findNavController(R.id.nav_host_fragment_home)
        NavigationUI.setupWithNavController( binding.bottomNavView, navController)

        bindOnClickListeners()
    }

    private fun bindOnClickListeners () {
        binding.toolbar.settingsBtn.setOnClickListener {
            navController.navigateUp();
            navController.navigate(R.id.settingsFragment)
        }
    }

    override fun onSendMessage(message: String) {
        chatViewModel.onSendMessage(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}