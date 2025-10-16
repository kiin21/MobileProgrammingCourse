package com.student.mylocalization

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.core.content.edit
import java.util.Locale

class MainActivity : ComponentActivity() {

    companion object {
        private const val PREFS_NAME = "MyPrefs"
        private const val KEY_LANGUAGE = "language"
        private const val DEFAULT_LANGUAGE = "en"

        // Language codes constants
        private const val LANG_ENGLISH = "en"
        private const val LANG_VIETNAMESE = "vi"
        private const val LANG_CHINESE = "zh"
    }

    private lateinit var sharedPreferences: SharedPreferences

    override fun attachBaseContext(newBase: Context) {
        sharedPreferences = newBase.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val lang = sharedPreferences.getString(KEY_LANGUAGE, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
        super.attachBaseContext(updateBaseContextLocale(newBase, Locale.forLanguageTag(lang)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupLanguageButtons()
    }

    private fun setupLanguageButtons() {
        findViewById<Button>(R.id.btnEnglish).setOnClickListener {
            setLocale(LANG_ENGLISH)
        }
        findViewById<Button>(R.id.btnVietnamese).setOnClickListener {
            setLocale(LANG_VIETNAMESE)
        }
        findViewById<Button>(R.id.btnChinese).setOnClickListener {
            setLocale(LANG_CHINESE)
        }
    }

    private fun setLocale(languageCode: String) {
        sharedPreferences.edit {
            putString(KEY_LANGUAGE, languageCode)
        }
        recreate()
    }

    private fun updateBaseContextLocale(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}