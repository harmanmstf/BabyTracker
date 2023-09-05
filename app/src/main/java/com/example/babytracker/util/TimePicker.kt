package com.example.babytracker.util

import android.app.TimePickerDialog
import android.content.Context
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TimePicker(private val context: Context) {

    private val calendar = Calendar.getInstance()

    fun showTimePickerDialog(timeTextView: TextView) {
        val timePickerDialog = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                updateTimeTextView(timeTextView)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
        timePickerDialog.show()
    }

    fun updateTimeTextView(timeTextView: TextView) {
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val formattedTime = timeFormat.format(calendar.time)
        timeTextView.text = formattedTime
    }
}
