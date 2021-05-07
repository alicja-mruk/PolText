package com.put.pt.poltext.screens.home.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.put.pt.poltext.databinding.FragmentSettingsBinding
import com.put.pt.poltext.screens.auth.login.LoginActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding ? = null
    private val binding get() = _binding!!


    private val viewModel by viewModel<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeFragment()
    }

    private fun initializeFragment(){
        bindOnClickListeners()
        observeLiveData()
    }

    private fun bindOnClickListeners(){
        binding.logoutBtn.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun observeLiveData(){
        with(viewModel){
            goToLoginScreen.observe(viewLifecycleOwner, {
                goToLoginScreen()
            })
        }
    }
    
    private fun goToLoginScreen(){
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

}