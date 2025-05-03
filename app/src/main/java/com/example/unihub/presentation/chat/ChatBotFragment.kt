package com.example.unihub.presentation.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unihub.databinding.FragmentChatBotBinding
import com.example.unihub.utils.provideNavigationHost

class ChatBotFragment : Fragment() {

    private lateinit var binding: FragmentChatBotBinding
    private lateinit var chatAdapter: ChatAdapter
    private val viewModel: ChatBotViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provideNavigationHost()?.hideBottomNavigationBar(false)

        // Set up RecyclerView
        chatAdapter = ChatAdapter()
        binding.rvMessages.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.btnSend.setOnClickListener {
            val userMessage = binding.etMessage.text.toString()
            if (userMessage.isNotBlank()) {
                chatAdapter.submitMessage(ChatMessage(userMessage, true))
                binding.etMessage.text.clear()
                viewModel.sendMessageToBot(userMessage)
            }
        }

        viewModel.botMessage.observe(viewLifecycleOwner) { botReply ->
            chatAdapter.submitMessage(ChatMessage(botReply, false))
        }

        // Helper buttons for quick responses
        binding.tvFirstHelp.setOnClickListener {
            binding.etMessage.setText(binding.tvFirstHelp.text)
        }
        binding.tvSecondHelp.setOnClickListener {
            binding.etMessage.setText(binding.tvSecondHelp.text)
        }
        binding.tvThirdHelp.setOnClickListener {
            binding.etMessage.setText(binding.tvThirdHelp.text)
        }

    }


}
