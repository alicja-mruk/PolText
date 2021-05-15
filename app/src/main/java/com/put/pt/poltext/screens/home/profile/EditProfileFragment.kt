package com.put.pt.poltext.screens.home.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.put.pt.poltext.databinding.FragmentEditProfileBinding
import com.put.pt.poltext.screens.home.ChatViewModel
import com.put.pt.poltext.utils.setOnSingleClickListener
import com.put.pt.poltext.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<ProfileViewModel>()
    private val chatViewModel by viewModel<ChatViewModel>()

    private lateinit var mListener: Listener

    interface Listener {
        fun onNavigateToProfileScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        registerOnClickListeners()
        observeLiveData()
    }

    private fun registerOnClickListeners() {
        binding.saveBtn.setOnSingleClickListener {
            val username = binding.name.text.toString()
            viewModel.updateUser(username)
        }

        binding.editNameBtn.setOnSingleClickListener {
            binding.name.requestFocus()
        }

        binding.layout.setOnClickListener {
            binding.name.clearFocus()
        }
    }

    private fun observeLiveData() {
        with(viewModel) {
            moveToProfileScreen.observe(viewLifecycleOwner, {
                mListener.onNavigateToProfileScreen()
            })

            user.observe(viewLifecycleOwner, { user ->
                binding.name.setText(user.username)
            })

            updateUserState.observe(viewLifecycleOwner, { state ->
                when (state) {
                    is ProfileViewModel.UIState.Success -> {
                        chatViewModel.refetchMessages()
                        context?.showToast(state.message)
                        binding.saveBtn.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                    }
                    is ProfileViewModel.UIState.Loading -> {
                        binding.saveBtn.visibility = View.INVISIBLE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ProfileViewModel.UIState.Failure -> {
                        binding.saveBtn.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        context?.showToast(state.message)
                    }
                }
            })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as Listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
}