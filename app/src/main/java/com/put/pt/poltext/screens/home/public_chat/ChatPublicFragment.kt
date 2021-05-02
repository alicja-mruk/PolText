package com.put.pt.poltext.screens.home.public_chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.put.pt.poltext.databinding.FragmentChatPublicBinding


class ChatPublicFragment : Fragment() {
    private var _binding: FragmentChatPublicBinding ? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatPublicBinding.inflate(inflater, container, false)
        return binding.root
    }

}