package com.aacademy.homework.data.preferences

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import javax.inject.Inject

class MyPreference @Inject constructor(private val prefs: SharedPreferences) {

    companion object {

        private const val THEME = "theme"
        private const val IMAGE_URL = "image_url"
    }

    var appTheme: Int
        get() = prefs.getInt(THEME, MODE_NIGHT_YES)
        set(value) {
            prefs.edit().putInt(THEME, value).apply()
        }

    var imageUrl: String
        get() = prefs.getString(IMAGE_URL, "")!!
        set(value) {
            prefs.edit().putString(IMAGE_URL, value).apply()
        }
}
