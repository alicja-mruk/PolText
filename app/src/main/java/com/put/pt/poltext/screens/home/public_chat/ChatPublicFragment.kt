package com.put.pt.poltext.screens.home.public_chat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.put.pt.poltext.databinding.FragmentChatPublicBinding
import com.put.pt.poltext.screens.home.ChatViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChatPublicFragment : Fragment() {
    private var _binding: FragmentChatPublicBinding ? = null
    private val binding get() = _binding!!

    private val viewModel by sharedViewModel<ChatViewModel>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var adapter: ChatPublicAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        initializeFragment()

    }

    private fun initializeFragment() {
        setUpRecyclerView()
        setUpAdapter()
        observeLiveData()
        bindOnClickListeners()
    }

    private fun setUpRecyclerView(){
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = linearLayoutManager
    }

    private fun setUpAdapter(){
        viewModel.let { viewModel ->
            adapter = ChatPublicAdapter(viewModel, this)
            binding.recyclerView.adapter = adapter
        }
    }

    private fun observeLiveData(){

    }

    private fun bindOnClickListeners(){
        binding.sendBtn.setOnClickListener{
            val text = binding.message.text.toString()
           // TODO: send message
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatPublicBinding.inflate(inflater, container, false)
        return binding.root
    }

}