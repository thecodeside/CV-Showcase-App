package com.thecodeside.cvshowcaseapp.features.about

import android.content.res.Resources
import androidx.annotation.StringRes
import com.thecodeside.cvshowcaseapp.R

// TODO: 1/10/21 Create global LCE?
sealed class AboutViewState {

    object Loading : AboutViewState()

    data class Content(
        val firstName: String,
        val lastName: String,
        val title: String,
        val summary: String,
        val profilePictureUrl: String,
    ) : AboutViewState()

    data class Error(
        private val messageString: String? = null,
        @StringRes
        private val messageRes: Int? = null,
    ) : AboutViewState() {
        fun parseErrorMessage(resources: Resources) = messageString
            ?: messageRes?.let { resources.getString(it) }
            ?: resources.getString(R.string.unknown_error)
    }
}

sealed class AboutViewEffect {
    object ProfilePictureKeepClicking : AboutViewEffect()
    object ProfilePictureEasterEgg : AboutViewEffect()
}
