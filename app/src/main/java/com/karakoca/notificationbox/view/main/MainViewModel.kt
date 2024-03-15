package com.karakoca.notificationbox.view.main

import androidx.lifecycle.ViewModel
import com.karakoca.notificationbox.util.PrefUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val prefs: PrefUtil) : ViewModel() {
    fun setTutorial() {
        prefs.setTutorialPassed()
    }

    fun getTutorialPassed(): Boolean {
        return prefs.getTutorialPassed()
    }
}