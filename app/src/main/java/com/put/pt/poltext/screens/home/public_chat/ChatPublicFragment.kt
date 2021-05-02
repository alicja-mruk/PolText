package com.put.pt.poltext.screens.home.public_chat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.put.pt.poltext.databinding.FragmentChatPublicBinding

class ChatPublicFragment : Fragment() {
    private var _binding: FragmentChatPublicBinding ? = null
    private val binding get() = _binding!!

    private lateinit var mListener: Listener

    interface Listener {
        fun onSendMessage(message: String)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        bindOnClickListeners()
    }

    private fun bindOnClickListeners(){
        binding.sendBtn.setOnClickListener{
            val text = binding.message.text.toString()
            mListener.onSendMessage(text)
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
        _binding = FragmentChatPublicBinding.inflate(inflater, container, false)
        return binding.root
    }

}