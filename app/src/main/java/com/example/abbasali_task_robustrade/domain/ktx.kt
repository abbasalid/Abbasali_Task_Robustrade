package com.example.abbasali_task_robustrade.domain
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*


fun String.toReadableDate(): String {
    return try {

        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        parser.timeZone = TimeZone.getTimeZone("UTC")
        val date = parser.parse(this) ?: return this


        val day = SimpleDateFormat("d", Locale.getDefault()).format(date).toInt()
        val suffix = when {
            day in 11..13 -> "th"
            day % 10 == 1 -> "st"
            day % 10 == 2 -> "nd"
            day % 10 == 3 -> "rd"
            else -> "th"
        }


        val formatter = SimpleDateFormat("MMM yyyy h:mm a", Locale.getDefault())
        formatter.timeZone = TimeZone.getDefault()
        val formatted = formatter.format(date)

        "$day$suffix $formatted".lowercase().replaceFirstChar { it.titlecase() }
    } catch (e: Exception) {
        Log.d("Date Error", "${e.message}")
        " "
    }
}
