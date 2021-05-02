package com.put.pt.poltext.screens.home.private_chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.put.pt.poltext.databinding.FragmentChatPrivateBinding

class ChatPrivateFragment : Fragment() {
    private var _binding: FragmentChatPrivateBinding ? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChatPrivateBinding.inflate(inflater, container, false)
        return binding.root
    }
}