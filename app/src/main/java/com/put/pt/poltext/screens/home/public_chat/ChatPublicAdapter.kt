package com.put.pt.poltext.screens.home.public_chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.put.pt.poltext.R
import com.put.pt.poltext.model.PublicChatMessage
import com.put.pt.poltext.screens.home.ChatViewModel


class ChatPublicAdapter(viewModel: ChatViewModel, fragment: ChatPublicFragment) :
    RecyclerView.Adapter<ChatPublicAdapter.ViewHolder>() {
    var messages: ArrayList<PublicChatMessage> = ArrayList()

    init {
        viewModel.messages.observe(fragment.viewLifecycleOwner) {
            messages.addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = messages[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.chat_public_row, parent, false))
    }

    override fun getItemCount(): Int = messages.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val message = view.findViewById(R.id.message_row) as TextView
        val timestamp = view.findViewById(R.id.timestamp) as TextView
        val name = view.findViewById(R.id.name_public_row) as TextView
        val image = view.findViewById(R.id.image_public_row) as ImageView

        fun bind(item: PublicChatMessage) {
            message.text = item.message
            timestamp.text = item.timestamp
            name.text = item.user.username
            item.user.photoUrl?.let {
                Glide.with(itemView).load(it).into(image)
            }
        }
    }
}
