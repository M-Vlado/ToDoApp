package sk.vmproject.todoapp.presentation.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


fun formattedDate(date: String): String {
    try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val outputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        val d = inputFormat.parse(date)
        val formattedDate = outputFormat.format(d!!)
        return formattedDate

    } catch (e: Exception) {
        return ""
    }
}