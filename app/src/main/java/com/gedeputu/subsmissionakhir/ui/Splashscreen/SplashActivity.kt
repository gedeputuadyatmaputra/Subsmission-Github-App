package com.gedeputu.subsmissionakhir.ui.Splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ViewPropertyAnimator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.gedeputu.subsmissionakhir.R
import com.gedeputu.subsmissionakhir.ui.MainActivity
import com.gedeputu.subsmissionakhir.ui.Setting.SettingPreferences
import com.gedeputu.subsmissionakhir.ui.Setting.SettingViewModel
import com.gedeputu.subsmissionakhir.ui.Setting.SettingViewModelFactory
import com.gedeputu.subsmissionakhir.ui.Setting.dataStore

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private var startSplash: ViewPropertyAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val img = findViewById<ImageView>(R.id.img_splash)
        val splashScreenTheme = findViewById<ConstraintLayout>(R.id.main)
        val pref = SettingPreferences.getInstance(dataStore)

        val settingViewModel =
            ViewModelProvider(this, SettingViewModelFactory(pref))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this)
        {
                isDarkModeActive: Boolean ->

            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                splashScreenTheme.setBackgroundColor(ContextCompat.getColor(this, androidx.appcompat.R.color.primary_material_dark))
                startSplash?.start()

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                splashScreenTheme.setBackgroundColor(ContextCompat.getColor(this, R.color.Primary))
                startSplash?.start()
            }
        }

        startSplash = img.animate().setDuration(splashDelay).alpha(1f).withEndAction {
            val intent = Intent(this, MainActivity::class.java)
            intent.apply {
                startActivity(this)
                finish()
            }
        }
    }

    override fun onDestroy() {
        startSplash?.cancel()
        super.onDestroy()
    }

    companion object {
        private var splashDelay: Long = 1_000L
    }
}