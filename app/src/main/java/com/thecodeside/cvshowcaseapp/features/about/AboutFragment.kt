package com.thecodeside.cvshowcaseapp.features.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.google.android.material.snackbar.Snackbar
import com.thecodeside.cvshowcaseapp.R
import com.thecodeside.cvshowcaseapp.common.ui.ViewBindingFragment
import com.thecodeside.cvshowcaseapp.common.utils.IntentUtils
import com.thecodeside.cvshowcaseapp.common.utils.clicks
import com.thecodeside.cvshowcaseapp.common.utils.windowedCount
import com.thecodeside.cvshowcaseapp.databinding.FragmentAboutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class AboutFragment : ViewBindingFragment<FragmentAboutBinding>() {

    private val viewModel: AboutViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect {
                handleViewState(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.viewEffect.collect {
                handleViewEffect(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            binding.profilePicture.clicks().windowedCount(
                PROFILE_PICTURE_CLICK_BUFFER,
                PROFILE_PICTURE_CLICK_TIME_WINDOW_MILLIS
            ).collect {
                Timber.d("Profile picture click count = $it")
                viewModel.profilePictureClick(it)
            }
        }
    }

    private fun handleViewEffect(state: AboutViewEffect) = when (state) {
        AboutViewEffect.ProfilePictureKeepClicking -> handleKeepClickingViewEffect()
        AboutViewEffect.ProfilePictureEasterEgg -> handleEasterEgg()
    }

    private fun handleKeepClickingViewEffect() {
        Toast.makeText(context, R.string.keep_clicking, Toast.LENGTH_SHORT).show()
    }

    private fun handleEasterEgg() {
        val uriString = resources.getString(R.string.easter_egg_url)
        IntentUtils.getForBrowser(uriString).also {
            startActivity(it)
        }
    }

    private fun handleViewState(state: AboutViewState) = when (state) {
        AboutViewState.Loading -> handleLoadingState()
        is AboutViewState.Content -> handleDataState(state)
        is AboutViewState.Error -> handleErrorState(state)
    }

    private fun handleLoadingState() {
        binding.loadingOverlay.root.isVisible = true
    }

    private fun handleDataState(state: AboutViewState.Content) {
        binding.loadingOverlay.root.isVisible = false
        binding.run {
            firstName.text = state.firstName
            lastName.text = state.lastName
            title.text = state.title
            summary.text = state.summary
            profilePicture.load(state.profilePictureUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_about_24dp)
                scale(Scale.FIT)
                transformations(CircleCropTransformation())
            }
        }
    }

    // TODO: 1/11/21 add possibility to refresh
    private fun handleErrorState(state: AboutViewState.Error) {
        binding.loadingOverlay.root.isVisible = false
        activity?.findViewById<View>(R.id.bottom_nav_view)?.let { bottomNavView ->
            Snackbar.make(bottomNavView, state.parseErrorMessage(resources), Snackbar.LENGTH_LONG)
                .apply {
                    anchorView = bottomNavView
                }.show()
        }
    }

    override fun inflateView(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentAboutBinding.inflate(inflater, parent, false)
}
