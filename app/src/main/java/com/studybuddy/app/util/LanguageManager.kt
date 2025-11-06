package com.studybuddy.app.util

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*

object LanguageManager {

    fun setLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
        } else {
            config.locale = locale
        }

        return context.createConfigurationContext(config)
    }
}
