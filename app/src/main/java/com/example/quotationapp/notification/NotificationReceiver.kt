package com.example.quotationapp.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val title = intent?.getStringExtra("NOTIF_TITLE") ?: "Quote of the Day"
        val description = intent?.getStringExtra("NOTIF_DESCRIPTION") ?: "Here is your quote."

        NotificationHelper.showNotification(context, title, description)
    }
}
