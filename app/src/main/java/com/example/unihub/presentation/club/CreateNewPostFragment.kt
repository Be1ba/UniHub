package com.example.unihub.presentation.club

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.unihub.R
import com.example.unihub.databinding.FragmentCreateNewPostBinding
import com.example.unihub.presentation.profile.ProfileViewModel
import com.example.unihub.utils.SharedProvider
import com.example.unihub.utils.provideNavigationHost
import java.io.ByteArrayOutputStream
import kotlin.getValue

class CreateNewPostFragment : Fragment() {

    private lateinit var binding: FragmentCreateNewPostBinding
    private val clubViewModel: ClubViewModel by viewModels()
    private var imageBase64: String? = null

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val bitmap = uriToBitmap(it)
            imageBase64 = bitmapToBase64(bitmap)
            binding.btnAddMedia.text = "Image selected"
        }
    }

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
        val sharedProvider = SharedProvider(requireContext())



        binding.run {
            val items = listOf("Request for event", "Create new post", "Create booking poster for event")
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, items)
            acTemplate.setAdapter(adapter)
//            acTemplate.setText("Choose template", false)
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
                pickImageLauncher.launch("image/*")
            }

            btnPost.setOnClickListener {
                clubViewModel.createPost(
                    token = sharedProvider.getToken(),
                    content = etDescription.text.toString(),
                    image = imageBase64.orEmpty(), // Use saved base64
                    title = etTitle.text.toString()
                )
            }
        }


        clubViewModel.createPostResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().popBackStack()
            }
        }

        clubViewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.tvError.text = it.message
            binding.tvError.visibility = View.VISIBLE
        }
    }

    private fun uriToBitmap(uri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
        } else {
            val source = ImageDecoder.createSource(requireContext().contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
        binding.imagePreview.setImageURI(uri)
        binding.imagePreview.visibility = View.VISIBLE
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    companion object {
        internal const val REQUEST_CODE_PICK_MEDIA = 1001
    }
}