package com.put.pt.poltext.screens.home.private_chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.put.pt.poltext.databinding.FragmentChatPrivateBinding
import com.put.pt.poltext.model.User
import com.put.pt.poltext.screens.home.private_chat.ChatPrivateFragmentLobby.Companion.USER_KEY
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ChatPrivateFragment : Fragment() {
    private var _binding: FragmentChatPrivateBinding ? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = arguments?.getSerializable(USER_KEY)
        setDataIntoFields(user as User)
    }

    private fun setDataIntoFields(user: User){
        //TODO: set data into fields
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChatPrivateBinding.inflate(inflater, container, false)
        return binding.root
    }
}