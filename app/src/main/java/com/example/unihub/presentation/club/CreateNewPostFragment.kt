package com.example.unihub.presentation.club

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentCreateNewPostBinding
import com.example.unihub.utils.provideNavigationHost

class CreateNewPostFragment : Fragment() {

    private lateinit var binding: FragmentCreateNewPostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNewPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.hideBottomNavigationBar(false)

        binding.run {
            val items = listOf("Request for event", "Create new post", "Create booking poster for event")
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, items)
            acTemplate.setAdapter(adapter)
            acTemplate.setText("Choose template", false)
            acTemplate.setOnClickListener {
                acTemplate.showDropDown()
            }

            acTemplate.setOnItemClickListener { _, _, position, _ ->
                when (items[position]) {
                    "Request for event" -> {
                        findNavController().navigate(CreateNewPostFragmentDirections.actionCreateNewPostFragmentToCreateEventFragment())
                    }
                    "Create new post" -> {
                        // Действие для "Create new post"
                    }
                    "Create booking poster for event" -> {
                        findNavController().navigate(CreateNewPostFragmentDirections.actionCreateNewPostFragmentToCreatePosterFragment())
                    }
                }
            }

            btnAddMedia.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                }
                startActivityForResult(intent, REQUEST_CODE_PICK_MEDIA)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_MEDIA && resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImageUri = data?.data
            // Здесь вы можете обработать выбранное изображение, например, отобразить его
            binding.btnAddMedia.text = selectedImageUri?.toString()
        }
    }

    companion object {
        internal const val REQUEST_CODE_PICK_MEDIA = 1001
    }
}