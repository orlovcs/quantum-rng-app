package com.crimsonlabs.orlovcs.reaction

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    private var currentTheme = ORIGINAL

    companion object {
        private const val KEY_THEME = "Theme"
        private const val ORIGINAL = R.style.AppTheme_Original
        private const val CYAN = R.style.AppTheme_Cyan
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        currentTheme = PreferenceManager.getDefaultSharedPreferences(this).getInt(KEY_THEME, ORIGINAL)
        super.onCreate(savedInstanceState)
    }

    protected fun setTheme() {
        setTheme(currentTheme)
    }
    protected fun switchTheme(){
        currentTheme = when(currentTheme) {
            ORIGINAL -> CYAN
            CYAN -> ORIGINAL
            else -> -1
        }
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt(KEY_THEME, currentTheme).apply()
    }

}