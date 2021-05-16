package com.put.pt.poltext.screens.home.private_chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.put.pt.poltext.databinding.FragmentChatPrivateBinding
import com.put.pt.poltext.model.User
import com.put.pt.poltext.screens.home.private_chat.ChatPrivateFragmentLobby.Companion.USER_KEY
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class ChatPrivateFragment : Fragment() {
    private var _binding: FragmentChatPrivateBinding ? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<PrivateChatViewModel>()
    private lateinit var messageAdapter: GroupAdapter<GroupieViewHolder>
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeLiveData()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userTo = arguments?.getSerializable(USER_KEY)
        setDataIntoFields(userTo as User)
        messageAdapter = GroupAdapter<GroupieViewHolder>()
        setUpAdapter()
        populateData()
        bindOnClickListeners()
    }

    private fun setDataIntoFields(userTo: User){
        //TODO: set data into fields
    }

    private fun observeLiveData() {
        with(viewModel) {

        }
    }

    private fun populateData(){

    }

    private fun bindOnClickListeners() {
        binding.button.setOnClickListener{
            val text = binding.editText.text.toString()
            viewModel.sendMessage(text)
            binding.editText.text.clear()
            binding.recyclerView.scrollToPosition(binding.recyclerView.adapter!!.itemCount.minus(1))
        }
    }

    private fun setUpAdapter(){
        binding.recyclerView.adapter = messageAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChatPrivateBinding.inflate(inflater, container, false)
        return binding.root
    }
}