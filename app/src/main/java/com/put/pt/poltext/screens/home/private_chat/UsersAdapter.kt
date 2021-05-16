package com.put.pt.poltext.screens.home.private_chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.put.pt.poltext.R
import com.put.pt.poltext.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi


class UsersAdapter @ExperimentalCoroutinesApi constructor(
    viewModel: PrivateChatViewModel,
    fragment: ChatPrivateFragmentLobby
) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    var users: ArrayList<User> = ArrayList()
    var onItemClick: ((User) -> Unit)? = null

    init {
        viewModel.users.observe(fragment.viewLifecycleOwner, {
            users.clear()
            users.addAll(it)
            notifyDataSetChanged()
        })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = users[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.chat_private_row, parent, false))
    }

    override fun getItemCount(): Int = users.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById(R.id.user_private_profile_username) as TextView
        val image = view.findViewById(R.id.user_private_profile_image) as ImageView

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(users[adapterPosition])
            }
        }

        fun bind(item: User) {
            name.text = item.username
            if (item.photoUrl != "") {
                item.photoUrl?.let {
                    Glide.with(itemView).load(it).into(image)
                }
            }
        }
    }
}
