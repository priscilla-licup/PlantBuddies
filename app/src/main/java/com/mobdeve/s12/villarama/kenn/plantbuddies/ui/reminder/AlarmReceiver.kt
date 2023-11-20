package com.mobdeve.s12.villarama.kenn.plantbuddies.ui.reminder

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val CHANNEL_ID = "task_notification_channel"
        const val NOTIFICATION_ID = 123
        const val TASK_NAME_EXTRA = "task_name"
    }


    override fun onReceive(context: Context?, intent: Intent?) {
        val taskName = intent?.getStringExtra(TASK_NAME_EXTRA)
        if (!taskName.isNullOrBlank()) {
            showNotification(context, taskName)
        }
        Toast.makeText(context, "Alarm received for task: $taskName", Toast.LENGTH_SHORT).show()
    }



    private fun showNotification(context: Context?, taskName: String?) {
        createNotificationChannel(context)

        context?.let {
            val builder = NotificationCompat.Builder(it, CHANNEL_ID)
                //.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("PlantBuddy: Reminder")
                .setContentText("Reminding you of '$taskName'!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(it)) {
                if (ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                notify(NOTIFICATION_ID, builder.build())
            }
        }
    }



    private fun createNotificationChannel(context: Context?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Task Notifications"
            val descriptionText = "Channel for task notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
