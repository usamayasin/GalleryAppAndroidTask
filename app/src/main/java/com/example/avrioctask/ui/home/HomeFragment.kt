package com.example.avrioctask.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.avrioctask.R
import com.example.avrioctask.adapters.AlbumsAdapter
import com.example.avrioctask.data.model.ContentState
import com.example.avrioctask.data.model.ErrorState
import com.example.avrioctask.data.model.LoadingState
import com.example.avrioctask.data.model.UIState
import com.example.avrioctask.databinding.HomeFragmentBinding
import com.example.avrioctask.ui.MainViewModel
import com.example.avrioctask.utils.Constants
import com.example.avrioctask.utils.gone
import com.example.avrioctask.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var binding: HomeFragmentBinding
    private lateinit var albumsAdapter: AlbumsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        albumsAdapter = AlbumsAdapter { albumName ->
            val bundle = bundleOf(Constants.BUNDLE_KEY to albumName)
            findNavController().navigate(
                R.id.toAlbumDetailFragment,
                bundle
            )
        }
        binding.albumsRv.adapter = albumsAdapter

        initObservers()

    }

    private fun initObservers() {
        mainViewModel.isGridLayoutManager.observe(viewLifecycleOwner) { isGridLayout ->
            if (isGridLayout) {
                binding.albumsRv.layoutManager = GridLayoutManager(requireContext(), 2)
            } else {
                binding.albumsRv.layoutManager = LinearLayoutManager(requireContext())
            }
        }

        viewModel.albumsLiveData.observe(viewLifecycleOwner) { response ->
            response?.let {
                albumsAdapter.differ.submitList(response)
            }
        }

        val uiStateObserver = Observer<UIState> { uiState ->
            when (uiState) {
                is LoadingState -> {
                    binding.errorMessage.gone()
                    binding.progressbar.visible()
                }

                is ContentState -> {
                    binding.errorMessage.gone()
                    binding.progressbar.gone()
                }

                is ErrorState -> {
                    binding.progressbar.gone()
                    binding.errorMessage.text = uiState.message
                    binding.errorMessage.visible()
                }
            }
        }
        viewModel.uiStateLiveData.observe(viewLifecycleOwner, uiStateObserver)
    }

    override fun onResume() {
        super.onResume()
        checkPermissions()
    }

    private fun checkPermissions() {
        val permissions = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.READ_MEDIA_IMAGES)

            permissions.add(Manifest.permission.READ_MEDIA_VIDEO)
        } else {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        val granted = permissions.all {
            ContextCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }

        if (granted) {
            viewModel.fetchAlbums()
        } else {
            requestMultiplePermissions.launch(permissions.toTypedArray())
        }
    }

    private fun shouldShowRequestPermissionRationale(permissions: List<String>): Boolean {
        return permissions.any {
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                it
            )
        }
    }

    private fun showPermissionRationale(permissions: List<String>) {}

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireContext().packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all { it.value }
            if (granted) {
                viewModel.fetchAlbums()
            } else {
                val deniedPermissions = permissions.filterValues { !it }.keys.toList()
                if (shouldShowRequestPermissionRationale(deniedPermissions)) {
                    showPermissionRationale(deniedPermissions)
                } else {
                    openAppSettings()
                }
            }
        }
}