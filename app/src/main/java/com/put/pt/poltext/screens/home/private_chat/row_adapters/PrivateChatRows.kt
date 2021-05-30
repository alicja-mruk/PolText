package com.put.pt.poltext.screens.home.private_chat.row_adapters

import com.put.pt.poltext.R
import com.put.pt.poltext.databinding.PrivateChatReceivedRowBinding
import com.put.pt.poltext.databinding.PrivateChatSendRowBinding
import com.put.pt.poltext.model.PrivateChatMessage
import com.xwray.groupie.databinding.BindableItem


class SendMessageItem(private val message: PrivateChatMessage) :
    BindableItem<PrivateChatSendRowBinding>() {
    override fun getLayout(): Int {
        return R.layout.private_chat_send_row
    }

    override fun bind(viewBinding: PrivateChatSendRowBinding, position: Int) {
        viewBinding.message = message
    }
}

class ReceiveMessageItem(private val message: PrivateChatMessage) :
    BindableItem<PrivateChatReceivedRowBinding>() {
    override fun getLayout(): Int {
        return R.layout.private_chat_received_row
    }

    override fun bind(viewBinding: PrivateChatReceivedRowBinding, position: Int) {
        viewBinding.message = message
    }
}