package com.example.avrioctask.ui.detail.video

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import com.example.avrioctask.databinding.ItemVideoBinding
import com.example.avrioctask.utils.Constants.ARG_URI

class VideoFragment: Fragment() {
    private lateinit var binding: ItemVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ItemVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_URI) }?.apply {
            val mediaController = MediaController(requireContext())
            mediaController.setAnchorView(binding.video)
            binding.video.setMediaController(mediaController)
            binding.video.setVideoURI(Uri.parse(getString(ARG_URI)))
            binding.video.requestFocus()
            binding.video.start()
        }
    }
}