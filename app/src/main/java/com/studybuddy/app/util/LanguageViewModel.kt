package com.studybuddy.app.util

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LanguageViewModel : ViewModel() {

    private val _language = MutableStateFlow("en")
    val language: StateFlow<String> = _language

    fun switchLanguage(languageCode: String) {
        viewModelScope.launch {
            _language.value = languageCode
        }
    }
}