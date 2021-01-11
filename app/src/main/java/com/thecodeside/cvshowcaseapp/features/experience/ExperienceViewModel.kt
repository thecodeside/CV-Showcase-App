package com.thecodeside.cvshowcaseapp.features.experience

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExperienceViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is experience Fragment\nStill under construction"
    }
    val text: LiveData<String> = _text
}
