package com.enriqueajin.newsapp.util

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object DateUtils {
    /**
     * Format ISO 8601 dates into 'MMMM dd, yyyy'. E.g. June 23, 2024
     */
    fun formatDate(date: String): String {
        return try {
            val localDateTime = ZonedDateTime.parse(date)
            val pattern = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
            localDateTime.format(pattern)
        } catch (e: Exception) {
            date
        }
    }
}