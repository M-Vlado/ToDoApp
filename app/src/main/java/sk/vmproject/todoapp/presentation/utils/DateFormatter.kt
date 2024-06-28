package sk.vmproject.todoapp.presentation.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


fun parseAndFormatDate(date: String): String {
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

fun createFormatedDate(date: Date): String {
    try {
        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("UTC")
        return outputFormat.format(date)
    } catch (e: Exception) {
        return ""
    }
}