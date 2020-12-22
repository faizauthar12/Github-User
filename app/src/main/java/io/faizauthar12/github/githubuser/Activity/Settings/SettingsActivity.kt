package io.faizauthar12.github.githubuser.Activity.Settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.faizauthar12.github.githubuser.Activity.Settings.Fragment.SettingsPreferenceFragment
import io.faizauthar12.github.githubuser.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setActionBar()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.setting_holder, SettingsPreferenceFragment(applicationContext))
            .commit()
    }

    private fun setActionBar() {
        if (supportActionBar != null) {
            supportActionBar?.apply {
                setDisplayShowTitleEnabled(true)
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
            title = resources.getString(R.string.settings)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}