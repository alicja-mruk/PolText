package com.put.pt.poltext.screens.home.settings


import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.put.pt.poltext.R
import com.put.pt.poltext.databinding.FragmentHelpBinding


class HelpFragment : Fragment() {

    private var _binding: FragmentHelpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHelpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val htmlAsSpanned = Html.fromHtml(context?.getString(R.string.rules))
        binding.chatRulesTv.text = htmlAsSpanned
    }
}