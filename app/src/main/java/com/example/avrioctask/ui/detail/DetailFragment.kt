package com.example.avrioctask.ui.detail

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.avrioctask.adapters.MediaAdapter
import com.example.avrioctask.databinding.DetailFragmentBinding
import com.example.avrioctask.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var binding: DetailFragmentBinding
    private lateinit var mediaAdapter: MediaAdapter
    private var albumName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailFragmentBinding.inflate(inflater, container, false)
        binding.mediaItemsRv.layoutManager = GridLayoutManager(requireContext(), 2)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.albumName = requireArguments().getString(Constants.BUNDLE_KEY)
        this.albumName?.let {
            viewModel.fetchMediaItems(it)
        }

        mediaAdapter = MediaAdapter(
            onImageClick = ::onImageClick,
            onVideoClick = ::onVideoClick
        )
        binding.mediaItemsRv.adapter = mediaAdapter

        viewModel.mediaLiveData.observe(viewLifecycleOwner) { response ->
            response?.let {
                mediaAdapter.differ.submitList(response)
            }
        }
    }


    private fun onImageClick(uri: Uri) {


    }

    private fun onVideoClick(uri: Uri) {
    }


}