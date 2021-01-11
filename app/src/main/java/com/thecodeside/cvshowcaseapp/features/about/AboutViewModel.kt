package com.thecodeside.cvshowcaseapp.features.about

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thecodeside.cvshowcaseapp.R
import com.thecodeside.cvshowcaseapp.common.utils.DispatcherProvider
import com.thecodeside.cvshowcaseapp.common.utils.guardCancellation
import com.thecodeside.cvshowcaseapp.common.utils.isNetworkFailure
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

const val PROFILE_PICTURE_CLICK_BUFFER = 7
const val PROFILE_PICTURE_CLICK_TIME_WINDOW_MILLIS = 5_000L
private val PROFILE_PICTURE_CLICK_NOTIFICATIONS = arrayOf(2, 5)

class AboutViewModel
@ViewModelInject constructor(
    private val fetchCvData: FetchCvDataUseCase,
    private val dispatchers: DispatcherProvider,
) : ViewModel() {

    private val _viewState = MutableStateFlow<AboutViewState>(AboutViewState.Loading)
    val viewState: StateFlow<AboutViewState> = _viewState

    private val _viewEffect = MutableSharedFlow<AboutViewEffect>()
    val viewEffect: SharedFlow<AboutViewEffect> = _viewEffect

    init {
        loadData()
    }

    fun loadData() {
        _viewState.value = AboutViewState.Loading
        viewModelScope.launch(dispatchers.io()) {
            val newState = try {
                val cvData = fetchCvData()
                AboutViewState.Content(
                    firstName = cvData.firstName,
                    lastName = cvData.lastName,
                    title = cvData.title,
                    summary = cvData.summary,
                    profilePictureUrl = cvData.imageUrl,
                )
            } catch (e: Exception) {
                Timber.e(e)
                e.guardCancellation()
                parseError(e)
            }
            withContext(dispatchers.main()) {
                _viewState.value = newState
            }
        }
    }

    private fun parseError(e: Exception) = if (e.isNetworkFailure()) {
        AboutViewState.Error(messageRes = R.string.unknown_error)
    } else {
        AboutViewState.Error(messageRes = R.string.network_error)
    }

    fun profilePictureClick(clickCount: Int) {
        val newViewEffect = when (clickCount) {
            in PROFILE_PICTURE_CLICK_NOTIFICATIONS -> AboutViewEffect.ProfilePictureKeepClicking
            PROFILE_PICTURE_CLICK_BUFFER -> AboutViewEffect.ProfilePictureEasterEgg
            else -> null
        } ?: return

        viewModelScope.launch {
            _viewEffect.emit(newViewEffect)
        }
    }
}
