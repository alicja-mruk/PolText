package com.put.pt.poltext.screens.auth.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.put.pt.poltext.databinding.FragmentRegisterUsernamePasswordBinding
import com.put.pt.poltext.utils.setOnSingleClickListener
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class RegisterUsernamePassword : Fragment() {
    private var _binding: FragmentRegisterUsernamePasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var mListener: Listener

    interface Listener {
        fun onRegister(username: String, password: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterUsernamePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.registerButton.setOnSingleClickListener {
            val fullName = binding.nameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            mListener.onRegister(fullName, password)
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as Listener
    }

    fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.registerButton.visibility = View.INVISIBLE
    }

    fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
        binding.registerButton.visibility = View.VISIBLE
    }

    companion object {
        const val TAG = "RegisterActivity"
    }
}