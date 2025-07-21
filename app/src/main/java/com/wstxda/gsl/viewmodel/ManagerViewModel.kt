package com.wstxda.gsl.viewmodel

import android.app.Application
import android.content.ComponentName
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wstxda.gsl.ui.ThemeManager

class ManagerViewModel(application: Application) : AndroidViewModel(application) {
    private val pm = application.packageManager
    private val _isAssistSetupDone = MutableLiveData<Boolean>()
    val isAssistSetupDone: LiveData<Boolean> = _isAssistSetupDone

    fun setAssistSetupDone(done: Boolean) {
        _isAssistSetupDone.value = done
    }

    fun toggleActivityVisibility(activityClass: Class<*>, show: Boolean) {
        val newState = if (show) PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        else PackageManager.COMPONENT_ENABLED_STATE_DISABLED

        val component = ComponentName(getApplication(), activityClass)
        pm.setComponentEnabledSetting(component, newState, PackageManager.DONT_KILL_APP)
    }

    fun applyTheme(themeKey: String) {
        ThemeManager.applyTheme(themeKey)
    }
}