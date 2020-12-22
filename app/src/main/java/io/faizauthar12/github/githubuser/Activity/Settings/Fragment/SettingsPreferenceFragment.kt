package io.faizauthar12.github.githubuser.Activity.Settings.Fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import io.faizauthar12.github.githubuser.Alarm.AlarmReceiver
import io.faizauthar12.github.githubuser.Alarm.AlarmReceiver.Companion.ID_REPEATING
import io.faizauthar12.github.githubuser.R
import java.util.*

class SettingsPreferenceFragment(appContext: Context) : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var reminderKey: String
    private lateinit var reminderPreference: SwitchPreference
    private lateinit var alarmReceiver: AlarmReceiver
    private var mContext = appContext

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        alarmReceiver = AlarmReceiver()
        init()
    }

    private fun init() {
        reminderKey = resources.getString(R.string.key_reminder)

        reminderPreference = findPreference<SwitchPreference>(reminderKey) as SwitchPreference
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == reminderKey){
            sharedPreferences?.let { setReminder(it.getBoolean(key,false)) }
        }
    }

    private fun setReminder(reminder: Boolean) {
        if(reminder){
            alarmReceiver.createAlarm(mContext,
                getString(R.string.app_name),
                resources.getString(R.string.notif_message),
                ID_REPEATING,
                Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 9)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                })
        }else{
            alarmReceiver.cancelAlarm(mContext, ID_REPEATING)
        }
    }
}