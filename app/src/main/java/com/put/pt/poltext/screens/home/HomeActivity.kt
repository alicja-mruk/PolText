package com.put.pt.poltext.screens.home


import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.put.pt.poltext.R
import com.put.pt.poltext.databinding.ActivityHomeBinding
import com.put.pt.poltext.screens.BaseActivity


class HomeActivity : BaseActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        bindOnClickListeners()
    }

    private fun bindOnClickListeners () {
        binding.include.settingsBtn.setOnClickListener {
            navHostFragment.navController.navigate(R.id.action_chatPrivateFragment_to_settingsFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}