package com.put.pt.poltext.screens.home

import android.os.Bundle
import com.put.pt.poltext.data.firebase.common.auth
import com.put.pt.poltext.databinding.ActivityHomeBinding
import com.put.pt.poltext.screens.BaseActivity

class HomeActivity : BaseActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logout.setOnClickListener {
            auth.signOut()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}