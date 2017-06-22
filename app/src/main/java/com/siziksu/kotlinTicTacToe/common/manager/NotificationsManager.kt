package com.siziksu.kotlinTicTacToe.common.manager

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.siziksu.kotlinTicTacToe.R
import com.siziksu.kotlinTicTacToe.common.Constants

class NotificationsManager(private val context: Context) {

    companion object {
        private const val TAG = "NotificationsManager"
        private const val NOTIFICATION_ID = 11471
        private const val NOTIFICATION_TITLE = "Game request from:"
    }

    fun showNotification(opponentEmail: String) {
        val notification = buildNotification(opponentEmail)
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun buildNotification(opponentEmail: String): Notification {
        val icon = BitmapFactory.decodeResource(context.resources, R.drawable.notification_icon)
        val intent = Intent(Constants.BROADCAST_ACTION_LISTENER)
        intent.putExtra(Constants.OPPONENT_EMAIL_KEY, opponentEmail)
        val pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        val builder = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_phone)
                .setLargeIcon(icon)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(opponentEmail)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        val notification = builder.build()
        return notification
    }
}
