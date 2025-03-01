package com.example.quotationapp.fragment.todayFragment

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.example.quotationapp.activity.MainActivity
import com.example.quotationapp.receiver.ReminderReceiver
import com.example.quotationapp.utlis.showDatePickerWithTime
import com.example.quotationapp.utlis.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

fun setReminder(todayFragment: TodayFragment, context: Activity, selectedHour: Int, selectedMinute: Int) {
    todayFragment.showDatePickerWithTime(todayFragment.calendar) { selectedDate, selectedTime ->
        CoroutineScope(Dispatchers.IO).launch {
            todayFragment.calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
            todayFragment.calendar.set(Calendar.MINUTE, selectedMinute)
            todayFragment.calendar.set(Calendar.SECOND, 0)

            setReminderAlarm(todayFragment, todayFragment.calendar)
        }
        context.toast("Reminder set for $selectedDate at $selectedTime")
    }
}

fun setReminderAlarm(todayFragment: TodayFragment, calendar: Calendar) {
    val alarmManager =
        todayFragment.requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
            data = Uri.fromParts("package", todayFragment.requireContext().packageName, null)
        }
        todayFragment.startActivity(intent)
        return
    }

    val intent = Intent(todayFragment.requireContext(), ReminderReceiver::class.java).apply {
        putExtra("REMINDER_TITLE", todayFragment.title)
        putExtra("REMINDER_DESC", todayFragment.description)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        todayFragment.requireContext(),
        System.currentTimeMillis().toInt(),  // Unique ID for each alarm
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    Log.d("Reminder", "Alarm set for: ${calendar.time}")
}





fun cancelReminderAlarm(todayFragment: TodayFragment) {
    val alarmManager =
        todayFragment.requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(todayFragment.requireContext(), MainActivity::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        todayFragment.requireContext(),
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    alarmManager.cancel(pendingIntent)
    Log.d("Reminder", "Alarm canceled")
}