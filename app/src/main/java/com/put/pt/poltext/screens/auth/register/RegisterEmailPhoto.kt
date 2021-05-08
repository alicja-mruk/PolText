package com.put.pt.poltext.screens.auth.register

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.put.pt.poltext.R
import com.put.pt.poltext.databinding.FragmentRegisterEmailPhotoBinding
import com.put.pt.poltext.databinding.FragmentRegisterUsernamePasswordBinding
import com.put.pt.poltext.extensions.setOnSingleClickListener
import com.put.pt.poltext.helper.ImagePicker
import com.put.pt.poltext.utils.activity

class RegisterEmailPhoto : Fragment() {
    private var _binding: FragmentRegisterEmailPhotoBinding? = null
    private val binding get() = _binding!!

    private lateinit var imagePicker: ImagePicker
    private lateinit var mListener: Listener

    interface Listener {
        fun onNext(email: String, photoUrl: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterEmailPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        registerOnClickListeners()

        imagePicker = ImagePicker(context?.activity() as AppCompatActivity) { uri ->
            binding.profileIv.setImageURI(uri)
            binding.profileIv.tag = uri.toString()
        }
    }

    private fun registerOnClickListeners() {
        binding.profileIv.setOnClickListener {
            imagePicker.show()
        }

        binding.nextButton.setOnSingleClickListener {
            val email = binding.emailEditText.text.toString()
            if (binding.profileIv.tag != null) {
                mListener.onNext(email, binding.profileIv.tag.toString())
            } else {
                mListener.onNext(email, "")
            }

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as Listener
    }
}