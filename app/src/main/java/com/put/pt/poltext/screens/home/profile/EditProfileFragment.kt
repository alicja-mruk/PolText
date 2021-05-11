package com.put.pt.poltext.screens.home.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.put.pt.poltext.databinding.FragmentEditProfileBinding
import com.put.pt.poltext.extensions.setOnSingleClickListener
import com.put.pt.poltext.helper.ImagePicker
import com.put.pt.poltext.utils.activity
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<ProfileViewModel>()

    private lateinit var mListener: Listener

    interface Listener {
        fun onNavigateToProfileScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        registerOnClickListeners()
        observeLiveData()
    }

    private fun registerOnClickListeners(){
        binding.saveBtn.setOnSingleClickListener {
            val username = binding.name.text.toString()
            val email = binding.email.text.toString()
            viewModel.updateUser(username, email)
        }

        binding.editEmailBtn.setOnSingleClickListener{
            binding.email.requestFocus()
        }
        binding.editNameBtn.setOnSingleClickListener{
            binding.name.requestFocus()
        }

        binding.layout.setOnClickListener{
            binding.email.clearFocus()
            binding.name.clearFocus()
        }
    }

    private fun observeLiveData(){
        viewModel.moveToProfileScreen.observe(viewLifecycleOwner,{
            mListener.onNavigateToProfileScreen()
        })
        viewModel.user.observe(viewLifecycleOwner,{ user ->
            binding.name.setText(user.username)
            binding.email.setText(user.email)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
}