package com.studybuddy.app.settings

import android.app.Activity
import android.content.Intent
import android.os.Build
import java.util.*

object LocaleManager {
    fun setLocale(activity: Activity, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = activity.resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
            activity.createConfigurationContext(config)
        } else {
            config.locale = locale
            activity.resources.updateConfiguration(config, activity.resources.displayMetrics)
        }
        // Restart activity to apply changes
        val i = Intent(activity, activity::class.java)
        activity.startActivity(i)
        activity.finish()
    }
}
