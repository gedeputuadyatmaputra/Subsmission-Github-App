package com.gedeputu.subsmissionakhir.ui.Setting

import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.gedeputu.subsmissionakhir.R
import com.gedeputu.subsmissionakhir.databinding.ActivitySettingthemeBinding



class SettingthemeActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingthemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivitySettingthemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pref = SettingPreferences.getInstance(dataStore)

        val settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(pref)
        )[SettingViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this)

        { isDarkModeActive: Boolean ->
            setNightModeSum(isDarkModeActive)
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
            setNightModeSum(isChecked)
            if (isChecked) {
                Toast.makeText(this@SettingthemeActivity, "Change to Dark Mode", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@SettingthemeActivity, "Change to Light Mode", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setNightModeSum(isNightMode: Boolean) {
        binding.tvtheme.text =
            if (isNightMode) resources.getString(R.string.light_mode) else resources.getString(
                R.string.dark_mode
            )
    }
}