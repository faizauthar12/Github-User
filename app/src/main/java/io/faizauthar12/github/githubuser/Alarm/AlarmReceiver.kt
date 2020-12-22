package io.faizauthar12.github.githubuser.Alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import io.faizauthar12.github.githubuser.MainActivity
import io.faizauthar12.github.githubuser.R
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val CHANNEL_ID = "channel_reminder"
        const val CHANNEL_NAME = "daily_reminder"
        const val EXTRA_TITLE = "title_reminder"
        const val EXTRA_MESSAGE = "message_reminder"
        const val ID_REPEATING = 100
    }

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val title = intent.getStringExtra(EXTRA_TITLE)
        val message = intent.getStringExtra(EXTRA_MESSAGE)

        val notificationIntent = Intent(context, MainActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }

        val pendingIntent = PendingIntent.getActivity(context,0,notificationIntent,0)
        showNotification(context,title ?: "title", message ?: "message", ID_REPEATING, pendingIntent)
    }

    private fun showNotification(
        context: Context,
        title: String,
        message: String,
        notificationId: Int,
        pendingIntent: PendingIntent?) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_octicons_mark_github)
            setContentTitle(title)
            setContentText(message)
            setContentIntent(pendingIntent)
            setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            setSound(alarmSound)
            setAutoCancel(true)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                enableVibration(true)
                vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            }

            notification.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(notificationId, notification.build())
    }

    fun createAlarm(context: Context, title: String, message: String, requestCode: Int, time: Calendar){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(EXTRA_TITLE, title)
            putExtra(EXTRA_MESSAGE, message)
        }
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)

        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                time.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        )
    }

    fun cancelAlarm(context: Context, requestCode: Int){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0).also {
            it.cancel()
        }

        alarmManager.cancel(pendingIntent)
    }
}