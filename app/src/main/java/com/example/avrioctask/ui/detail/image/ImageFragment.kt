package com.example.avrioctask.ui.detail.image

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.avrioctask.databinding.ItemImageBinding
import com.example.avrioctask.utils.Constants.ARG_URI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageFragment : Fragment() {
    private lateinit var binding: ItemImageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ItemImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_URI) }?.apply {
            Glide.with(view.context)
                .load(Uri.parse(getString(ARG_URI)))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.image)

        }
    }
}