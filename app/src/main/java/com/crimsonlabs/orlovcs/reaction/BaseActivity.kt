package com.crimsonlabs.orlovcs.reaction

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log

abstract class BaseActivity : AppCompatActivity() {

    private var currentTheme = ORIGINAL

    companion object {
        private const val KEY_THEME = "Theme"
        private const val ORIGINAL = R.style.AppTheme_Original
        private const val CYAN = R.style.AppTheme_Cyan
    }
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentTheme = PreferenceManager.getDefaultSharedPreferences(this).getInt(KEY_THEME, ORIGINAL)
        Log.i("BASE CREATE", currentTheme.toString())
    }
    protected fun setTheme() {
        setTheme(currentTheme)
    }
    protected fun baseRecreate() {
        recreate()
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