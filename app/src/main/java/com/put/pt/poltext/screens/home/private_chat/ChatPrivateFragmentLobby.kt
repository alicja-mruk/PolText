package com.put.pt.poltext.screens.home.private_chat

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.put.pt.poltext.R
import com.put.pt.poltext.databinding.FragmentChatPrivateLobbyBinding
import com.put.pt.poltext.model.User
import com.put.pt.poltext.screens.State
import com.put.pt.poltext.screens.home.profile.ProfileFragment
import com.put.pt.poltext.screens.home.public_chat.ChatPublicAdapter
import com.put.pt.poltext.screens.home.public_chat.PublicChatViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
@ExperimentalCoroutinesApi
class ChatPrivateFragmentLobby : Fragment() {
    private var _binding: FragmentChatPrivateLobbyBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<PrivateChatViewModel>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var adapter: ChatPrivateAdapter? = null

    private lateinit var mListener: Listener

    interface Listener {
        fun onNavigateToChatPublicFragment(user: User)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChatPrivateLobbyBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun initializeFragment() {
        setUpRecyclerView()
        setUpAdapter()
        observeLiveData()
        bindOnClickListeners()
    }

    private fun bindOnClickListeners() {

    }

    private fun observeLiveData() {
        with(viewModel) {
            state.observe(viewLifecycleOwner, { state ->
                when (state) {
                    State.LOADING -> shouldProgressBarBeShown(true)
                    State.SUCCESS -> {
                        shouldProgressBarBeShown(false)
                    }
                    else -> Unit
                }
            })
        }
    }

    private fun shouldProgressBarBeShown(isShown: Boolean) {
        if (isShown) {
            binding.progressBar.visibility = View.VISIBLE
            binding.usersRecyclerView.visibility = View.INVISIBLE
            binding.noMessagesText.visibility = View.GONE
        } else {
            if (viewModel.users.value?.size == 0) {
                binding.usersRecyclerView.visibility = View.INVISIBLE
                binding.noMessagesText.visibility = View.VISIBLE
            } else {
                binding.usersRecyclerView.visibility = View.VISIBLE
                binding.noMessagesText.visibility = View.GONE
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setUpAdapter() {
        viewModel.let { viewModel ->
            adapter = ChatPrivateAdapter(viewModel, this)
            binding.usersRecyclerView.adapter = adapter
        }

        adapter?.onItemClick = { user ->
            mListener.onNavigateToChatPublicFragment(user)
        }
    }

    private fun setUpRecyclerView() {
        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.usersRecyclerView.layoutManager = linearLayoutManager
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as Listener
    }

    companion object {
        const val USER_KEY = "user"
    }
}