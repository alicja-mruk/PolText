package com.put.pt.poltext.screens.auth.register


import android.os.Bundle
import com.put.pt.poltext.databinding.ActivityRegisterBinding
import com.put.pt.poltext.screens.BaseActivity


class RegisterActivity : BaseActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}