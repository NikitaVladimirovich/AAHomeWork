package com.aacademy.homework.data.preferences

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import javax.inject.Inject

class MyPreference @Inject constructor(private val prefs: SharedPreferences) {

    companion object {

        private const val THEME = "theme"
    }

    var appTheme: Int
        get() = prefs.getInt(THEME, MODE_NIGHT_YES)
        set(value) {
            prefs.edit().putInt(THEME, value).apply()
        }
}
