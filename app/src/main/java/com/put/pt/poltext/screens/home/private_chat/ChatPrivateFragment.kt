package com.put.pt.poltext.screens.home.private_chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.put.pt.poltext.R
import com.put.pt.poltext.data.firebase.common.auth
import com.put.pt.poltext.databinding.FragmentChatPrivateBinding
import com.put.pt.poltext.model.User
import com.put.pt.poltext.screens.home.private_chat.ChatPrivateFragmentLobby.Companion.USER_KEY
import com.put.pt.poltext.screens.home.private_chat.row_adapters.ReceiveMessageItem
import com.put.pt.poltext.screens.home.private_chat.row_adapters.SendMessageItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class ChatPrivateFragment : Fragment() {
    private var _binding: FragmentChatPrivateBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<PrivateChatViewModel>()
    private lateinit var messageAdapter: GroupAdapter<GroupieViewHolder>
    private lateinit var userTo: User

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeLiveData()
        setUpAdapter()
        bindOnClickListeners()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userTo = arguments?.getSerializable(USER_KEY) as User
    }

    private fun observeLiveData() {
        with(viewModel) {
            messages.observe(viewLifecycleOwner, { data ->
                data.forEach { item ->
                    if (item.userFrom == auth.currentUser.uid && item.userTo == userTo.uid) {
                        messageAdapter.add(SendMessageItem(item))
                    } else if (item.userTo == auth.currentUser.uid && item.userFrom == userTo.uid) {
                        messageAdapter.add(ReceiveMessageItem(item))
                    }
                }
            })
            chatItemState.observe(viewLifecycleOwner, { state ->
                when (state) {
                    is PrivateChatViewModel.ChatItemState.Empty -> {
                        showSpinnerOrEmptyTextView(null)
                    }
                    is PrivateChatViewModel.ChatItemState.Loading -> {
                        showSpinnerOrEmptyTextView(true)
                    }
                    is PrivateChatViewModel.ChatItemState.Success -> {
                        showSpinnerOrEmptyTextView(false)
                    }
                    is PrivateChatViewModel.ChatItemState.SendMessageLoading -> {

                    }
                    is PrivateChatViewModel.ChatItemState.SendMessageSuccess -> {

                    }
                    else -> Unit
                }
            })
        }
    }

    /**
     * If true show spinner.
     * If false hide spinner.
     * If null show info about no messages yet.
     */
    private fun showSpinnerOrEmptyTextView(isSpinnnerVisible: Boolean?) {
        when (isSpinnnerVisible) {
            true -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.VISIBLE
                binding.emptyText.visibility = View.GONE
            }
            false -> {
                binding.progressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                binding.emptyText.visibility = View.GONE
            }
            else -> {
                binding.progressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.INVISIBLE
                binding.emptyText.visibility = View.VISIBLE
            }
        }
    }

    private fun bindOnClickListeners() {
        binding.button.setOnClickListener {
            val text = binding.editText.text.toString()
            viewModel.listenToMessages()
            viewModel.sendMessage(text, userTo.uid)
            binding.editText.text.clear()
        }
    }

    private fun setUpAdapter() {
        messageAdapter = GroupAdapter<GroupieViewHolder>()
        binding.recyclerView.adapter = messageAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_private, container, false)
        return binding.root
    }
}