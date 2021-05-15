package com.put.pt.poltext.screens.home.public_chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.put.pt.poltext.databinding.FragmentChatPublicBinding
import com.put.pt.poltext.screens.State
import com.put.pt.poltext.screens.auth.login.LoginActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class ChatPublicFragment : Fragment() {
    private var _binding: FragmentChatPublicBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<PublicChatViewModel>()
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

    private fun setUpRecyclerView() {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = linearLayoutManager
    }

    private fun setUpAdapter() {
        viewModel.let { viewModel ->
            adapter = ChatPublicAdapter(viewModel, this)
            binding.recyclerView.adapter = adapter
        }
    }

    private fun observeLiveData() {
        with(viewModel) {
            resetInput.observe(viewLifecycleOwner, {
                binding.message.setText("")
            })
            notifyDataSetChanged.observe(viewLifecycleOwner, {
                adapter?.notifyDataSetChanged()
                binding.recyclerView.scrollToPosition(binding.recyclerView.adapter!!.itemCount - 1)
            })
            state.observe(viewLifecycleOwner, { state ->
                when (state) {
                    State.LOADING -> shouldProgressBarBeShown(true)
                    State.SUCCESS -> {
                        shouldProgressBarBeShown(false)
                        refreshView()
                    }
                    else -> Unit
                }
            })
            moveToLoginScreen.observe(viewLifecycleOwner, {
                startLoginActivity()
            })
        }
    }

    private fun bindOnClickListeners() {
        binding.sendBtn.setOnClickListener {
            val text = binding.message.text.toString()
            viewModel.onSendMessage(text)
        }
    }

    private fun shouldProgressBarBeShown(isShown: Boolean) {
        if (isShown) {
            binding.progressBar.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.INVISIBLE
            binding.noMessagesLayout.visibility = View.GONE
        } else {
            if (viewModel.messages.value?.size == 0) {
                binding.recyclerView.visibility = View.INVISIBLE
                binding.noMessagesLayout.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.noMessagesLayout.visibility = View.GONE
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun refreshView() {
        binding.publicChatLayout.visibility = View.GONE
        binding.publicChatLayout.visibility = View.VISIBLE
    }

    private fun startLoginActivity() {
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatPublicBinding.inflate(inflater, container, false)
        return binding.root
    }
}