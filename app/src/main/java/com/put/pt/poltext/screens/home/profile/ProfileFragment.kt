package com.put.pt.poltext.screens.home.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.put.pt.poltext.databinding.FragmentProfileBinding
import com.put.pt.poltext.utils.setOnSingleClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<ProfileViewModel>()

    private lateinit var mListener: Listener

    interface Listener {
        fun onNavigateToEditProfileScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeLiveData()
        binding.navigateToEditProfileScreenBtn.setOnSingleClickListener {
            mListener.onNavigateToEditProfileScreen()
        }
    }

    private fun observeLiveData(){
        viewModel.user.observe(viewLifecycleOwner, {user ->
            binding.name.text = user.username
            binding.email.text = user.email
            user.photoUrl?.let {
                Glide.with(this).load(it).into(binding.imageView)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as Listener
    }

}